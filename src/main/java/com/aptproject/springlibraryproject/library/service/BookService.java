package com.aptproject.springlibraryproject.library.service;

import com.aptproject.springlibraryproject.library.dto.BookDTO;
import com.aptproject.springlibraryproject.library.mapper.BookMapper;
import com.aptproject.springlibraryproject.library.model.Author;
import com.aptproject.springlibraryproject.library.model.Book;
import com.aptproject.springlibraryproject.library.repository.AuthorRepository;
import com.aptproject.springlibraryproject.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class BookService
        extends GenericService<Book, BookDTO>{

    private final AuthorRepository authorRepository;

    public BookService(BookRepository repository,
                          BookMapper mapper,
                          AuthorRepository authorRepository) {
        super(repository, mapper);
        this.authorRepository = authorRepository;
    }

    public BookDTO addAuthor(final Long bookId,
                             final Long authorId) {
        BookDTO book = getOne(bookId);
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException("автор не найден"));
        book.getAuthorIds().add(author.getId());
        update(book);
        return book;
    }

}
