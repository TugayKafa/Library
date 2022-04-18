package com.endava.services.bookService;

import com.endava.dto.external.BookAssignment;
import com.endava.model.dto.BookRequest;
import com.endava.model.dto.DeactivateBookDto;
import com.endava.model.entity.Book;
import com.endava.specification.SpecificationParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface BookService {

    Set<Book> findBookSetByIsbnSet(Set<String> booksIsbn);

    Book findBookByIsbn(String isbn);

    Book addBook(BookRequest book, MultipartFile coverImage);

    Book updateBook(BookRequest book, MultipartFile coverImage);

    Page<Book> getPageOfBooks(SpecificationParams specificationParams, Pageable pageable);

    String deactivateBook(DeactivateBookDto bookDto);

    void updateBookQuantityAfterAssignment(List<Long> bookIds);

    List<BookAssignment> getSpecificationBooks(SpecificationParams specificationParams, List<Long> ids);
}
