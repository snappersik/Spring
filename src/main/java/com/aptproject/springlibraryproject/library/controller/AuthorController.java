package com.aptproject.springlibraryproject.library.controller;

import com.aptproject.springlibraryproject.library.model.Author;
import com.aptproject.springlibraryproject.library.repository.AuthorRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/authors") // http://localhost:8080/authors
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @RequestMapping(value = "/getOneById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> getOneById(@RequestParam(value = "id") Long id) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Данные найдены")));

            }

    }

