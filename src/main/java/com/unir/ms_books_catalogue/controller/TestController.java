package com.unir.ms_books_catalogue.controller;

import com.unir.ms_books_catalogue.data.model.Book;
import com.unir.ms_books_catalogue.service.BookServices;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class TestController {
     private final BookServices service;

       public TestController(BookServices service) {
           this.service = service;
       }

    // Búsqueda por todos los atributos (individual o combinada)
    //@Operation(summary="search books")
    @GetMapping()
    public List<Book> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publicationDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Boolean visible
    ) {
        return service.search(title, author, publicationDate, category, isbn, rating, visible);
    }
    /*
        //@Operation(summary="get a book given a book id")
        @GetMapping("/{id}")
        public Book getBook(@PathVariable Long id) {
            return service.getById(id);
        }

        //@Operation(summary="post a book given a book from body")
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public Book create(@RequestBody Book book) {
            return service.create(book);
        }

        //@Operation(summary="put a book given a book id from a body")
        @PutMapping("/{id}")
        public Book update(@PathVariable Long id, @RequestBody Book book) {
            return service.update(id, book);
        }

        //@Operation(summary="patch a book given a book id from a body")
        @PatchMapping("/{id}")
        public Book patch(@PathVariable Long id, @RequestBody Book book) {
            return service.patch(id, book);
        }

        //@Operation(summary="delete a book given a book id")
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(@PathVariable Long id) {
            service.delete(id);
        }*/
    @GetMapping("/ping")
    public String ping() {
        return "OK...pong";
    }
}
