package com.endava.services.conversionService;

import com.endava.model.dto.BookRequest;
import com.endava.model.dto.BookResponse;
import com.endava.model.entity.Book;
import com.endava.model.entity.Genre;
import com.endava.model.entity.Language;
import com.endava.model.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Service
public class ConversionServiceImpl implements ConversionService {

    private final ModelMapper modelMapper;

    public ConversionServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookResponse convertBookToResponseBookResponse(Book book, String imageUrl) {
        BookResponse response = this.modelMapper.map(book, BookResponse.class);
        response.setYearOfPublication(Year.of(book.getYearOfPublication()));
        Set<String> genres = new HashSet<>();
        for (Genre genre : book.getGenres()) {
            genres.add(genre.getName());
        }
        response.setGenres(genres);
        Set<String> tags = new HashSet<>();
        for (Tag tag : book.getTags()) {
            tags.add(tag.getName());
        }
        response.setTags(tags);
        response.setCoverImageUrl(imageUrl);
        response.setStatus(book.getStatus());
        response.setReason(book.getReason());
        response.setDateOfDeactivation(book.getDateOfDeactivation());
        return response;
    }

    @Override
    public Book convertBookRequestToBook(BookRequest bookRequest, String imageUrl, String hash) {
        Book book = new Book();
        book.setLanguage(Language.valueOf(bookRequest.getLanguage().toUpperCase()));
        book.setPublisherName(bookRequest.getPublisherName());
        book.setQuantity(bookRequest.getQuantity());
        book.setDateAdded(LocalDate.now());
        book.setTitle(bookRequest.getTitle());
        book.setYearOfPublication((short) bookRequest.getYearOfPublication().getValue());
        book.setCoverImageUrl(imageUrl);
        book.setReason(null);
        book.setDateOfDeactivation(null);
        book.setIsbn(bookRequest.getIsbn());
        return book;
    }
}
