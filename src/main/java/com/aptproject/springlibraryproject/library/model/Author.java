package com.aptproject.springlibraryproject.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "authors_sequence", allocationSize = 1)
public class Author extends GenericModel {

    @Column(name = "name", nullable = false)
    private String authorName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "description")
    private String description;

    @JsonIgnore // Избавляет от ошибки Stack Overflow, JSON зацикливается: У автора есть книга, у книги - автор, у автора книга...
    @ManyToMany (cascade = {CascadeType.PERSIST})
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "author_id"), foreignKey = @ForeignKey (name = "FK_AUTHORS_BOOKS"),
            inverseJoinColumns = @JoinColumn(name = "book_id"), inverseForeignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"))
    List<Book> books;
}
