package com.unir.ms_books_catalogue.data.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Book not found with id " + id);
    }
}
