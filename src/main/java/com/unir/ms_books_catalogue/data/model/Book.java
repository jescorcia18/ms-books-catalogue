package com.unir.ms_books_catalogue.data.model;
//import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

//@Entity
@Data
//@Table(name = "books")
@Document(indexName = "books")
public class Book {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field(type = FieldType.Keyword)
    private String id;

    //@Column(nullable = false)
    // Título: autocompletado y búsqueda flexible
    @Field(type = FieldType.Search_As_You_Type)
    private String title;

    //@Column(nullable = false)
    // Autor: texto completo
    @Field(type = FieldType.Text)
    private String author;

    // Fecha de publicación
    @Field(type = FieldType.Date)
    private LocalDate publicationDate;

    // Categoría: perfecta para filtros/facets
    @Field(type = FieldType.Keyword)
    private String category;

    //@Column(unique = true)
    // ISBN único, búsqueda exacta
    @Field(type = FieldType.Keyword)
    private String isbn;

    // 1 a 5
    @Field(type = FieldType.Integer)
    private Integer rating;

    // true = visible en el front, false = oculto
    @Field(type = FieldType.Boolean)
    private Boolean visible;
}
