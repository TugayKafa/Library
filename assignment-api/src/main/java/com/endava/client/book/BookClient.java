package com.endava.client.book;

import com.endava.dto.book.BookDetails;
import com.endava.dto.external.ExternalBookDto;
import com.endava.specification.BookSpecificationParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@FeignClient(name = "bookClient", url = "${book-api.url}")
public interface BookClient {

    @GetMapping(value = "/books/internal/getBookSetByIsbnSet")
    Set<ExternalBookDto> getBookSetByIsbnSet(@RequestBody Set<String> isbnSet);

    @GetMapping(value = "books/internal/getBook")
    ExternalBookDto getBookByIsbn (@RequestBody String isbn);

    @PatchMapping(value = "/books/internal/updateBookQuantity")
    void updateBookQuantity(@RequestBody List<Long> bookIds);

    @GetMapping(value = "/books/internal/bookIds")
    List<BookDetails> getBookSpecifications(@RequestBody BookSpecificationParams specificationParams, @RequestParam List<Long> bookIds);
}
