package com.aptproject.springlibraryproject.dbexample.model;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    private String title;
    private String author;
    private Date dateAdded;
}