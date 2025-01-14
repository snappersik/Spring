package com.aptproject.springlibraryproject.library.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookSearchDTO {
    private String bookTitle;
    private String authorName;
    private String genre;
}
