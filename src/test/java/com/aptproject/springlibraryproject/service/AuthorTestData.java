package com.aptproject.springlibraryproject.service;

import com.aptproject.springlibraryproject.library.dto.AuthorDTO;
import com.aptproject.springlibraryproject.library.model.Author;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface AuthorTestData {
    AuthorDTO AUTHOR_DTO_1 = new AuthorDTO(
            "authorName1",
            LocalDate.now(),
            "description1",
            new ArrayList<>()
    );

    AuthorDTO AUTHOR_DTO_2 = new AuthorDTO(
            "authorName2",
            LocalDate.now(),
            "description2",
            new ArrayList<>()
    );

    AuthorDTO AUTHOR_DTO_3_DELETED = new AuthorDTO(
            "authorName3",
            LocalDate.now(),
            "description3",
            new ArrayList<>()
    );

    List<AuthorDTO> AUTHOR_DTO_LIST = Arrays.asList(
            AUTHOR_DTO_1,
            AUTHOR_DTO_2,
            AUTHOR_DTO_3_DELETED
    );

    Author AUTHOR_1 = new Author("author1",
    LocalDate.now(),
    "description1",
    null);
    Author AUTHOR_2 = new Author("author2",
    LocalDate.now(),
    "description2",
    null);
    Author AUTHOR_3 = new Author("author3",
    LocalDate.now(),
    "description3",
    null);
    List<Author> AUTHOR_LIST = Arrays.asList(AUTHOR_1, AUTHOR_2, AUTHOR_3);
}
