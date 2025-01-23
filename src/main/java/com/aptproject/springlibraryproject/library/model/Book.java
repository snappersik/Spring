package com.aptproject.springlibraryproject.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table (name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "books_sequence", allocationSize = 1)
public class Book extends GenericModel {

    @Column(name = "title", nullable = false)
    private String bookTitle;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "storage_place", nullable = false)
    private String storagePlace;

    @Column(name = "online_copy_path")
    private String onlineCopyPath;

    @Column(name = "genre", nullable = false)
    @Enumerated
    private Genre genre;


    @Column(name = "description")
    private String description;


    @JsonIgnore // Избавляет от ошибки Stack Overflow, JSON зацикливается: У книги есть автор, у автора есть книга, у книги есть автор...
    @ManyToMany (cascade = {CascadeType.PERSIST})
    @JoinTable(name = "books_authors", // чтобы таблицы books и authors стали равнозначными. До этого authors была главнее
            joinColumns = @JoinColumn(name = "book_id"), foreignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"),
            inverseJoinColumns = @JoinColumn(name = "author_id"), inverseForeignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"))

    List<Author> authors;
}
