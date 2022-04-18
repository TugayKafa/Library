package com.endava.services.csvService;

import com.endava.exception.FailToExportDataException;
import com.endava.model.dto.AuthorBase;
import com.endava.model.dto.BookResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvServiceImpl implements CsvService {

    @Override
    public ByteArrayInputStream exportBooks(List<BookResponse> books) {
        final CSVFormat format = CSVFormat.DEFAULT.withHeader
                ("ISBN", "Title", "Publisher", "Published", "Authors", "Genres", "Quantity",
                        "Tags", "Language", "Added", "Status", "Reason", "Deactivation", "Image URL");

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            for (BookResponse book : books) {
                List<String> authorsList = new ArrayList<>();

                for (AuthorBase author : book.getAuthors()) {
                    List<String> authorList = new ArrayList<>();
                    authorList.add(author.getFirstName() + " " + author.getLastName());
                    authorsList.add(authorList.toString());
                }

                List<String> data = Arrays.asList(
                        book.getIsbn(),
                        book.getTitle(),
                        book.getPublisherName(),
                        book.getYearOfPublication().toString(),
                        authorsList.toString()
                                .replace("[", "")
                                .replace("]", ""),
                        book.getGenres().toString()
                                .replace("[", "")
                                .replace("]", ""),
                        book.getQuantity().toString(),
                        book.getTags().toString()
                                .replace("[", "")
                                .replace("]", ""),
                        book.getLanguage(),
                        valueOf(book.getDateAdded()),
                        valueOf(book.getStatus()),
                        valueOf(book.getReason()),
                        valueOf(book.getDateOfDeactivation()),
                        valueOf(book.getCoverImageUrl())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new FailToExportDataException();
        }
    }

    private String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}
