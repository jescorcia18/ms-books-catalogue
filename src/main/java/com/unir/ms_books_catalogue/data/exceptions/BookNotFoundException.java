package com.unir.ms_books_catalogue.data.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String id) {
        super("Book not found with id " + id);
    }
}
