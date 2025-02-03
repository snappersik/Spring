package com.aptproject.springlibraryproject.library.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddBookDTO {
    Long bookId;
    Long authorId;
}
