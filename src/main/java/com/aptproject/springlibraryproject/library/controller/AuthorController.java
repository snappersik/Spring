package com.aptproject.springlibraryproject.library.controller;

import com.aptproject.springlibraryproject.library.model.Author;
import com.aptproject.springlibraryproject.library.model.Book;
import com.aptproject.springlibraryproject.library.repository.AuthorRepository;
import com.aptproject.springlibraryproject.library.repository.BookRepository;
import com.aptproject.springlibraryproject.library.repository.GenericRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/authors") // http://localhost:8080/authors
@Tag(name = "Авторы", description = "Контроллер для работы с авторами из библиотеки")
public class AuthorController extends GenericController<Author> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    protected AuthorController(GenericRepository<Author> genericRepository, BookRepository bookRepository,
                               AuthorRepository authorRepository) {
        super(genericRepository);
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

        @Operation(description = "Добавить книгу к автору")
        @RequestMapping(value = "/addBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Author> addBook(@RequestParam(value = "bookId") Long bookId,
                                              @RequestParam(value = "authorId") Long authorId) {
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Книга не найдена"));
            Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException("Автор не найден"));
            author.getBooks().add(book);
            return ResponseEntity.status(HttpStatus.OK).body(authorRepository.save(author));
        }

    }

