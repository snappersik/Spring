package com.aptproject.springlibraryproject.library.controllers.rest;

import com.aptproject.springlibraryproject.library.dto.BookDTO;
import com.aptproject.springlibraryproject.library.model.Book;
import com.aptproject.springlibraryproject.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/books")
@Tag(name = "Книги",
        description = "Контроллер для работы с книгами библиотеки")
public class BookController
        extends GenericController<Book, BookDTO> {
    public BookController(BookService bookService) {
        super(bookService);
    }

    @Operation(description = "Добавить книгу к автору")
    @RequestMapping(value = "/addAuthor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> addAuthor(@RequestParam(value = "bookId") Long bookId,
                                             @RequestParam(value = "authorId") Long authorId) {

        return ResponseEntity.status(HttpStatus.OK).body(((BookService) service).addAuthor(bookId, authorId));
    }
}
