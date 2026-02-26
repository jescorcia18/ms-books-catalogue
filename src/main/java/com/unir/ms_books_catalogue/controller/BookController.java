package com.unir.ms_books_catalogue.controller;

import com.unir.ms_books_catalogue.data.Dto.BookSearchResponse;
import com.unir.ms_books_catalogue.data.model.Book;
import com.unir.ms_books_catalogue.service.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/books")

@Tag(name = "Books", description = "Operaciones sobre el catálogo de libros")
public class BookController {
     private final BookServices service;

       public BookController(BookServices service) {
           this.service = service;
       }

    // Búsqueda por todos los atributos (individual o combinada)
    @Operation(summary = "search books from filters (full-text + facets)")
    @GetMapping
    public BookSearchResponse searchBooks(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publicationDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Boolean visible
    ) {
        return service.search(q, title, author, publicationDate, category, isbn, rating, visible);
    }


    @Operation(summary="get a book given a book id")
        @GetMapping("/{id}")
        public Book getBook(@PathVariable String id) {
            return service.getById(id);
        }

        @Operation(summary="post a book given a book from body")
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public Book create(@RequestBody Book book) {
            return service.create(book);
        }

        @Operation(summary="put a book given a book id from a body")
       @PutMapping("/{id}")
        public Book update(@PathVariable String id, @RequestBody Book book) {
            return service.update(id, book);
        }

        @Operation(summary="patch a book given a book id from a body")
        @PatchMapping("/{id}")
        public Book patch(@PathVariable String id, @RequestBody Book book) {
            return service.patch(id, book);
        }

        @Operation(summary="delete a book given a book id")
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(@PathVariable String id) {
            service.delete(id);
        }
    @GetMapping("/ping")
    public String ping() {
        return "OK...pong";
    }
}
