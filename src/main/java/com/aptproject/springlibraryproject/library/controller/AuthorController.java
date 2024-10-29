package com.aptproject.springlibraryproject.library.controller;

import com.aptproject.springlibraryproject.library.model.Author;
import com.aptproject.springlibraryproject.library.repository.AuthorRepository;
import com.aptproject.springlibraryproject.library.repository.GenericRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors") // http://localhost:8080/authors
@Tag(name = "Авторы", description = "Контроллер для работы с авторами из библиотеки")
public class AuthorController extends GenericController<Author> {

    public AuthorController(GenericRepository<Author> genericRepository) {
        super(genericRepository);
    }

//    @Operation(description = "Получить запись по ID", method = "getOneById")
//    @RequestMapping(value = "/getOneById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Author> getOneById(@RequestParam(value = "id") Long id) {
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Данные найдены")));
//
//            }

    }

