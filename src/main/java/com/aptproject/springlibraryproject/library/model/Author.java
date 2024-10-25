package com.aptproject.springlibraryproject.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

    @Entity
    @Table
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Author extends GenericModel {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_generator")
        private Long id;

        @Column(name = "name", nullable = false)
        private String authorName;

        @Column(name = "birth_date")
        private LocalDate birthDate;

        @Column(name = "description")
        private String description;
}
