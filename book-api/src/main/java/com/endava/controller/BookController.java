package com.endava.controller;

import com.endava.client.assignment.AssignmentClient;
import com.endava.dto.external.BookAssignment;
import com.endava.dto.external.BookAssignmentDto;
import com.endava.model.dto.BookRequest;
import com.endava.model.dto.BookResponse;
import com.endava.model.dto.DeactivateBookDto;
import com.endava.model.entity.Book;
import com.endava.services.bookService.BookService;
import com.endava.services.conversionService.ConversionService;
import com.endava.services.csvService.CsvService;
import com.endava.specification.SpecificationParams;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ConversionService conversionService;
    private final ModelMapper modelMapper;
    private final CsvService csvService;
    private final AssignmentClient assignmentClient;

    public BookController(BookService bookService, ConversionService conversionService, CsvService csvService, ModelMapper modelMapper, AssignmentClient assignmentClient) {
        this.bookService = bookService;
        this.conversionService = conversionService;
        this.csvService = csvService;
        this.modelMapper = modelMapper;
        this.assignmentClient = assignmentClient;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<BookResponse> addBook(@Valid BookRequest bookRequest) {
        Book book = bookService.addBook(bookRequest, bookRequest.getCoverImage());
        BookResponse bookResponse = this.conversionService.convertBookToResponseBookResponse(book, book.getCoverImageUrl());
        return ResponseEntity.ok(bookResponse);
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<BookResponse> updateBookQuantity(@Valid BookRequest bookRequest) {
        Book updatedBook = this.bookService.updateBook(bookRequest, bookRequest.getCoverImage());
        BookResponse mappedDto = this.conversionService.convertBookToResponseBookResponse(updatedBook, updatedBook.getCoverImageUrl());
        return ResponseEntity.ok(mappedDto);
    }

    @GetMapping
    public Page<BookResponse> findAll(SpecificationParams specificationParams,
                                      @PageableDefault(sort = {"title"}, direction = Sort.Direction.ASC, value = 5)
                                              Pageable pageable) {

        Page<Book> page = bookService.getPageOfBooks(specificationParams, pageable);

        return page.map(book -> conversionService.convertBookToResponseBookResponse(book, book.getCoverImageUrl()));
    }

    @GetMapping("/csv")
    public ResponseEntity<Resource> getCSVFile(SpecificationParams specificationParams,
                                               @PageableDefault(sort = {"title"}, direction = Sort.Direction.ASC, value = 5)
                                                       Pageable pageable) {

        Page<Book> page = bookService.getPageOfBooks(specificationParams, pageable);
        Page<BookResponse> mappedDto = page.map(book -> conversionService
                .convertBookToResponseBookResponse(book, book.getCoverImageUrl()));

        String fileName = "books.csv";
        InputStreamResource file = new InputStreamResource(csvService.exportBooks(mappedDto.toList()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @PatchMapping("/deactivate")
    public String deactivateBook(@Valid @RequestBody DeactivateBookDto bookDto) {
        return bookService.deactivateBook(bookDto);
    }

    @GetMapping("/internal/getBookSetByIsbnSet")
    public Set<BookAssignmentDto> getBookSetByIsbnSet(@RequestBody Set<String> isbnSet) {
        Set<Book> bookSet = bookService.findBookSetByIsbnSet(isbnSet);
        return bookSet.stream().map(book -> modelMapper.map(book, BookAssignmentDto.class)).collect(Collectors.toSet());
    }

    @GetMapping(value = "/internal/getBook")
    public BookAssignmentDto getBookByIsbn (@RequestBody String isbn){
        Book book = bookService.findBookByIsbn(isbn);
        return modelMapper.map(book, BookAssignmentDto.class);
    }

    @PatchMapping("/internal/updateBookQuantity")
    public void updateBookQuantity(@RequestBody List<Long> bookIds) {
        this.bookService.updateBookQuantityAfterAssignment(bookIds);
    }

    @GetMapping("/internal/bookIds")
    public List<BookAssignment> getSpecificationUsers(@RequestBody SpecificationParams specificationParams, @RequestParam List<Long> bookIds) {
        return this.bookService.getSpecificationBooks(specificationParams, bookIds);
    }
}
