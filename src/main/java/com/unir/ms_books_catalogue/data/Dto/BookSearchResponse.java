package com.unir.ms_books_catalogue.data.Dto;

import com.unir.ms_books_catalogue.data.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class BookSearchResponse {

    private List<Book> items;

    // facets: nombre -> lista de (valor, conteo)
    private Map<String, List<FacetEntry>> facets;

    @Data
    @AllArgsConstructor
    public static class FacetEntry {
        private String value;
        private long count;
    }
}