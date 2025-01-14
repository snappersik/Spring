package com.aptproject.springlibraryproject.library.dto;

import com.aptproject.springlibraryproject.library.model.Author;
import com.aptproject.springlibraryproject.library.model.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class BookDTO extends GenericDTO {

    private String bookTitle;
    private String publisher;
    private LocalDate publishDate;
    private Integer amount;
    private String storagePlace;
    private String onlineCopyPath;
    private Genre genre;
    private String description;
    private List<Long> authorIds;
    private List<AuthorDTO> authorInfo;
}
