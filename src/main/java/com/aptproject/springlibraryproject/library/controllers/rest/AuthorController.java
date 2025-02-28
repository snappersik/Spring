package com.aptproject.springlibraryproject.library.controllers.rest;

import com.aptproject.springlibraryproject.library.dto.AddBookDTO;
import com.aptproject.springlibraryproject.library.dto.AuthorDTO;
import com.aptproject.springlibraryproject.library.model.Author;
import com.aptproject.springlibraryproject.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rest/authors") // http://localhost:8080/authors
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Авторы", description = "Контроллер для работы с авторами из библиотеки")
public class AuthorController
        extends GenericController<Author, AuthorDTO> {

    public AuthorController(AuthorService authorService) {
        super(authorService);
    }

    @Operation(description = "Добавить книгу к автору")
    @RequestMapping(value = "/addBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> addBook(@RequestParam(value = "bookId") Long bookId,
                                             @RequestParam(value = "authorId") Long authorId) {
        AddBookDTO addBookDTO = new AddBookDTO();
        addBookDTO.setAuthorId(authorId);
        addBookDTO.setBookId(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(((AuthorService) service).addBook(addBookDTO));
    }

}