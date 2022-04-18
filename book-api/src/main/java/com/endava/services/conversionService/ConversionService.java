package com.endava.services.conversionService;

import com.endava.model.dto.BookRequest;
import com.endava.model.dto.BookResponse;
import com.endava.model.entity.Book;

public interface ConversionService {
    BookResponse convertBookToResponseBookResponse(Book book, String imageUrl);

    Book convertBookRequestToBook(BookRequest bookRequest, String imageUrl, String hash);
}
