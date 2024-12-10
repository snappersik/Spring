package com.aptproject.springlibraryproject.dbexamples.model;

import com.aptproject.springlibraryproject.library.model.GenericModel;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book extends GenericModel {
    private Long id;
    private String title;
    private String author;
    private Date dateAdded;
}