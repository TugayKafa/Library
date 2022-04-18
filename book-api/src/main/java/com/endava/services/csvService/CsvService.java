package com.endava.services.csvService;

import com.endava.model.dto.BookResponse;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface CsvService {

    ByteArrayInputStream exportBooks(List<BookResponse> books);
}