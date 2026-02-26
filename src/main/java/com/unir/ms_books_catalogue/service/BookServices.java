package com.unir.ms_books_catalogue.service;

import com.unir.ms_books_catalogue.data.exceptions.BookNotFoundException;
import com.unir.ms_books_catalogue.data.model.Book;
import com.unir.ms_books_catalogue.data.repository.BookRepository;
import com.unir.ms_books_catalogue.data.Dto.BookSearchResponse;
import com.unir.ms_books_catalogue.data.Dto.BookSearchResponse.FacetEntry;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookServices {

    private final BookRepository repository;
/**
 * Búsqueda sencilla en Elasticsearch:
 * - Si viene q: filtramos por título/autor conteniendo q (full-text simple).
 * - Además aplicamos filtros exactos por el resto de campos.
 * - Calculamos facets por categoría y rating en memoria.
 */
public BookSearchResponse search(
        String q,
        String title,
        String author,
        LocalDate publicationDate,
        String category,
        String isbn,
        Integer rating,
        Boolean visible
) {
    // 1) Traemos todos los documentos (para demo / actividad es aceptable)
    //List<Book> all = (List<Book>) repository.findAll();
    Iterable<Book> iterable = repository.findAll();

    List<Book> all = new ArrayList<>();
    iterable.forEach(all::add);

    // 2) Filtro en memoria simulando lógica de búsqueda
    List<Book> filtered = all.stream()
            .filter(b -> matchesText(b, q))
            .filter(b -> matchesField(b.getTitle(), title))
            .filter(b -> matchesField(b.getAufthor(), author))
            .filter(b -> matchesDate(b.getPublicationDate(), publicationDate))
            .filter(b -> matchesField(b.getCategory(), category))
            .filter(b -> matchesField(b.getIsbn(), isbn))
            .filter(b -> matchesInteger(b.getRating(), rating))
            .filter(b -> matchesBoolean(b.getVisible(), visible))
            .collect(Collectors.toList());

    // 3) Facets sencillas por categoría y rating
    Map<String, List<FacetEntry>> facets = new HashMap<>();
    facets.put("category", buildFacet(filtered, Book::getCategory));
    facets.put("rating", buildFacet(filtered, b -> b.getRating() == null ? null : b.getRating().toString()));

    return new BookSearchResponse(filtered, facets);
}

private boolean matchesText(Book b, String q) {
    if (q == null || q.isBlank()) return true;
    String qLower = q.toLowerCase();
    return (b.getTitle() != null && b.getTitle().toLowerCase().contains(qLower))
            || (b.getAuthor() != null && b.getAuthor().toLowerCase().contains(qLower));
}

private boolean matchesField(String value, String filter) {
    if (filter == null || filter.isBlank()) return true;
    return value != null && value.equalsIgnoreCase(filter);
}

private boolean matchesDate(LocalDate value, LocalDate filter) {
    if (filter == null) return true;
    return filter.equals(value);
}

private boolean matchesInteger(Integer value, Integer filter) {
    if (filter == null) return true;
    return Objects.equals(value, filter);
}

private boolean matchesBoolean(Boolean value, Boolean filter) {
    if (filter == null) return true;
    return Objects.equals(value, filter);
}

private List<FacetEntry> buildFacet(List<Book> books, java.util.function.Function<Book, String> extractor) {
    return books.stream()
            .map(extractor)
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(v -> v, Collectors.counting()))
            .entrySet().stream()
            .map(e -> new FacetEntry(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(FacetEntry::getValue).reversed())
            .collect(Collectors.toList());
}

public Book create(Book book) {
    book.setId(null); // por si viene algo en el body
    return repository.save(book);
}

public Book update(String id, Book updated) {
    return repository.findById(id)
            .map(existing -> {
                existing.setTitle(updated.getTitle());
                existing.setAuthor(updated.getAuthor());
                existing.setPublicationDate(updated.getPublicationDate());
                existing.setCategory(updated.getCategory());
                existing.setIsbn(updated.getIsbn());
                existing.setRating(updated.getRating());
                existing.setVisible(updated.getVisible());
                return repository.save(existing);
            })
            .orElseThrow(() -> new BookNotFoundException(id));
}

public Book patch(String id, Book partial) {
    return repository.findById(id)
            .map(existing -> {
                if (partial.getTitle() != null) existing.setTitle(partial.getTitle());
                if (partial.getAuthor() != null) existing.setAuthor(partial.getAuthor());
                if (partial.getPublicationDate() != null) existing.setPublicationDate(partial.getPublicationDate());
                if (partial.getCategory() != null) existing.setCategory(partial.getCategory());
                if (partial.getIsbn() != null) existing.setIsbn(partial.getIsbn());
                if (partial.getRating() != null) existing.setRating(partial.getRating());
                if (partial.getVisible() != null) existing.setVisible(partial.getVisible());
                return repository.save(existing);
            })
            .orElseThrow(() -> new BookNotFoundException(id));
}

public void delete(String id) {
    if (!repository.existsById(id)) {
        throw new BookNotFoundException(id);
    }
    repository.deleteById(id);
}

public Book getById(String id) {
    return repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
}
}