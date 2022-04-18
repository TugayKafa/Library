package com.endava.services.bookService;


import com.endava.client.assignment.AssignmentClient;
import com.endava.dto.external.AuthorAssignment;
import com.endava.dto.external.BookAssignment;
import com.endava.exception.BookAlreadyDeactivatedException;
import com.endava.exception.BookNotFoundException;
import com.endava.exception.BookWithActiveAssignment;
import com.endava.exception.CoverImageAlreadyInUseException;
import com.endava.exception.EmptyCoverImageException;
import com.endava.exception.EmptyGenresException;
import com.endava.exception.ISBNExistException;
import com.endava.exception.ISBNNotExistsException;
import com.endava.exception.InvalidGenresException;
import com.endava.exception.WrongFileExtensionException;
import com.endava.model.dto.AuthorBase;
import com.endava.model.dto.BookRequest;
import com.endava.model.dto.DeactivateBookDto;
import com.endava.model.entity.Author;
import com.endava.model.entity.Book;
import com.endava.model.entity.Genre;
import com.endava.model.entity.Language;
import com.endava.model.entity.Reason;
import com.endava.model.entity.Status;
import com.endava.model.entity.Tag;
import com.endava.repostitory.AuthorRepository;
import com.endava.repostitory.BookRepository;
import com.endava.repostitory.GenreRepository;
import com.endava.repostitory.TagRepository;
import com.endava.services.conversionService.ConversionService;
import com.endava.services.firebaseService.FirebaseService;
import com.endava.services.hashService.HashService;
import com.endava.specification.BookSpecification;
import com.endava.specification.SpecificationParams;
import com.endava.util.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.endava.util.Constants.EXTENSION_JPEG;
import static com.endava.util.Constants.EXTENSION_JPG;
import static com.endava.util.Constants.EXTENSION_PNG;
import static com.endava.util.Constants.POINT_SEPARATOR;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final FirebaseService firebaseService;
    private final TagRepository tagRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private final ConversionService conversionService;
    private final HashService hashService;
    private final AssignmentClient assignmentClient;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, FirebaseService firebaseService, TagRepository tagRepository, GenreRepository genreRepository, ModelMapper modelMapper, ConversionService conversionService, HashService hashService, AssignmentClient assignmentClient) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.firebaseService = firebaseService;
        this.tagRepository = tagRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
        this.conversionService = conversionService;
        this.hashService = hashService;
        this.assignmentClient = assignmentClient;
    }

    @Override
    public Book addBook(BookRequest bookRequest, MultipartFile coverImage) {
        trimRequestData(bookRequest);
        if (bookRepository.findByIsbn(bookRequest.getIsbn()).isPresent()) {
            throw new ISBNExistException();
        }
        Book book;
        if (coverImage == null || coverImage.isEmpty() || coverImage.getOriginalFilename() == null) {
            throw new EmptyCoverImageException();
        }
        String extension = this.getExtension(coverImage.getOriginalFilename());
        checkExtensionIsValid(extension);
        String hashValueFileName = hashService.createHash(coverImage).concat(extension);

        Optional<Book> bookByCoverImageUrlContaining = bookRepository.findByCoverImageUrlContaining(hashValueFileName);
        if (bookByCoverImageUrlContaining.isPresent()) {
            throw new CoverImageAlreadyInUseException();
        }
        book = conversionService.convertBookRequestToBook(bookRequest, firebaseService.uploadFile(coverImage, hashValueFileName), hashValueFileName);

        book.setAuthors(getAuthors(bookRequest));
        book.setGenres(getGenres(bookRequest));
        book.setTags(getTags(bookRequest));

        return bookRepository.save(book);
    }

    private void checkExtensionIsValid(String extension) {
        if (!(extension.equals(EXTENSION_JPG) || extension.equals(EXTENSION_JPEG) || extension.equals(EXTENSION_PNG))) {
            throw new WrongFileExtensionException();
        }
    }

    private void trimRequestData(BookRequest bookRequest) {
        bookRequest.setAuthors(bookRequest.getAuthors().stream().peek(a -> {
            a.setFirstName(a.getFirstName().trim());
            a.setLastName(a.getLastName().trim());
        }).collect(Collectors.toList()));
        bookRequest.setPublisherName(bookRequest.getPublisherName().trim());
        bookRequest.setTitle(bookRequest.getTitle().trim());
        bookRequest.setLanguage(bookRequest.getLanguage().trim());
        bookRequest.setIsbn(bookRequest.getIsbn().trim());
    }

    private Set<Tag> getTags(BookRequest bookRequest) {
        Set<Tag> tags = new LinkedHashSet<>();
        if (bookRequest.getTags() == null || bookRequest.getTags().isEmpty()) {
            return tags;
        }
        for (String tagName : bookRequest.getTags()) {
            tags.add(tagRepository.findByName(tagName).orElse(new Tag(tagName)));
        }
        return tags;
    }

    private Set<Genre> getGenres(BookRequest bookRequest) {
        Set<Genre> genres = new LinkedHashSet<>();
        if (bookRequest.getGenres() == null || bookRequest.getGenres().isEmpty()) {
            throw new EmptyGenresException();
        }
        List<String> invalidGenres = new ArrayList<>();
        for (String genreName : bookRequest.getGenres()) {
            if (genreRepository.findByName(genreName.toLowerCase(Locale.ROOT)).isPresent()) {
                genreRepository.findByName(genreName.toLowerCase(Locale.ROOT)).ifPresent(genres::add);
            } else invalidGenres.add(genreName);
        }
        if (!invalidGenres.isEmpty()) {
            throw new InvalidGenresException(invalidGenres);
        }
        return genres;
    }

    private List<Author> getAuthors(BookRequest bookRequest) {
        List<Author> authors = new ArrayList<>();
        for (AuthorBase authorBase : bookRequest.getAuthors()) {
            authors.add(authorRepository.findByFirstNameAndLastName(authorBase.getFirstName(), authorBase.getLastName()).orElseGet(() -> modelMapper.map(authorBase, Author.class)));
        }
        return authors;
    }

    @Override
    public Book updateBook(BookRequest bookRequest, MultipartFile coverImage) {
        trimRequestData(bookRequest);
        Book foundBook = this.bookRepository.findByIsbn(bookRequest.getIsbn())
                .orElseThrow(ISBNNotExistsException::new);

        if (coverImage == null || coverImage.isEmpty() || coverImage.getOriginalFilename() == null) {
            throw new EmptyCoverImageException();
        }
        String extension = this.getExtension(coverImage.getOriginalFilename());
        checkExtensionIsValid(extension);
        String hashValueFileName = hashService.createHash(coverImage).concat(extension);
        String imageUrl = foundBook.getCoverImageUrl();
        String oldImageFileName = foundBook.getCoverImageUrl()
                .substring(foundBook.getCoverImageUrl().lastIndexOf("/") + 1, foundBook.getCoverImageUrl().lastIndexOf("?"));
        if (!oldImageFileName.equals(hashValueFileName)) {
            Optional<Book> bookByCoverImageUrlContaining = bookRepository.findByCoverImageUrlContaining(hashValueFileName);
            if (bookByCoverImageUrlContaining.isPresent()) {
                throw new CoverImageAlreadyInUseException();
            }
            imageUrl = firebaseService.uploadFile(coverImage, hashValueFileName);
        }
        foundBook.setPublisherName(bookRequest.getPublisherName());
        foundBook.setTitle(bookRequest.getTitle());
        foundBook.setQuantity(bookRequest.getQuantity());
        foundBook.setLanguage(Language.valueOf(bookRequest.getLanguage().toUpperCase()));
        foundBook.setYearOfPublication((short) bookRequest.getYearOfPublication().getValue());
        foundBook.setCoverImageUrl(imageUrl);
        foundBook.setGenres(getGenres(bookRequest));
        foundBook.setAuthors(getAuthors(bookRequest));
        foundBook.setTags(getTags(bookRequest));
        return bookRepository.save(foundBook);
    }

    @Override
    public Page<Book> getPageOfBooks(SpecificationParams specificationParams, Pageable pageable) {
        return bookRepository.findAll(BookSpecification.getSearchSpecification(specificationParams), pageable);
    }

    @Override
    public Set<Book> findBookSetByIsbnSet(Set<String> booksIsbn) {
        Set<Book> bookSet = new LinkedHashSet<>();
        for (String isbn : booksIsbn) {
            bookSet.add(this.bookRepository.findByIsbn(isbn).orElseThrow(ISBNNotExistsException::new));
        }
        return bookSet;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(ISBNNotExistsException::new);
    }


    @Override
    public String deactivateBook(DeactivateBookDto bookDto) {

        Book book = bookRepository.findByIsbn(bookDto.getIsbn())
                .orElseThrow(BookNotFoundException::new);

        if (book.getReason() != null) {
            throw new BookAlreadyDeactivatedException(book.getReason(), book.getDescription(), book.getDateOfDeactivation().toString());
        }

        if (isBookAssigned(book.getId())) {
            throw new BookWithActiveAssignment();
        } else {
            Reason reason = Reason.valueOf(bookDto.getReason().toUpperCase(Locale.ROOT));
            book.setReason(reason);
            book.setDateOfDeactivation(LocalDate.now());
            book.setStatus(Status.INACTIVE);
            if (bookDto.getDescription() == null) {
                book.setDescription(book.getReason().getDescription());
            } else {
                book.setDescription(bookDto.getDescription());
            }
            this.bookRepository.save(book);
            return String.format(Constants.SUCCESSFULLY_DEACTIVATED, book.getIsbn(), book.getReason(), book.getDescription(),
                    book.getDateOfDeactivation().toString());
        }
    }

    private boolean isBookAssigned(Long bookId) {
        return assignmentClient.isBookHaveAssignment(bookId);
    }

    @Override
    public void updateBookQuantityAfterAssignment(List<Long> bookIds) {
        List<Book> booksToUpdate = this.bookRepository.findAllById(bookIds);
        booksToUpdate.forEach(book -> book.setQuantity(book.getQuantity() - 1));
        this.bookRepository.saveAll(booksToUpdate);
    }

    private String getExtension(String fileName) {
        if (fileName.isEmpty()) {
            return Constants.EMPTY_STRING;
        }
        return fileName.substring(fileName.lastIndexOf(POINT_SEPARATOR));
    }

    @Override
    public List<BookAssignment> getSpecificationBooks(SpecificationParams specificationParams, List<Long> ids) {
        List<BookAssignment> specificationBooks = new ArrayList<>();
        Specification<Book> searchSpecification = BookSpecification.getSearchSpecification(specificationParams);
        List<Book> allBySpecification = this.bookRepository.findAll(searchSpecification);
        for (Book book : allBySpecification) {
            if (ids.contains(book.getId())) {
                specificationBooks.add(mapToBookAssignments(book));
            }
        }
        return specificationBooks;
    }

    private BookAssignment mapToBookAssignments(Book book) {
        BookAssignment bookAssignment = new BookAssignment();
        bookAssignment.setTitle(book.getTitle());
        List<AuthorAssignment> authorAssignments = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            AuthorAssignment authorAssignment = new AuthorAssignment();
            authorAssignment.setFirstName(author.getFirstName());
            authorAssignment.setLastName(author.getLastName());
            authorAssignments.add(authorAssignment);
        }
        bookAssignment.setAuthors(authorAssignments);
        return bookAssignment;
    }

}