package com.aptproject.springlibraryproject.library.service;

import com.aptproject.springlibraryproject.library.constants.Errors;
import com.aptproject.springlibraryproject.library.dto.BookDTO;
import com.aptproject.springlibraryproject.library.dto.BookSearchDTO;
import com.aptproject.springlibraryproject.library.exception.MyDeleteException;
import com.aptproject.springlibraryproject.library.mapper.BookMapper;
import com.aptproject.springlibraryproject.library.model.Author;
import com.aptproject.springlibraryproject.library.model.Book;
import com.aptproject.springlibraryproject.library.repository.AuthorRepository;
import com.aptproject.springlibraryproject.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class BookService
        extends GenericService<Book, BookDTO> {
    private final AuthorRepository authorRepository;

    protected BookService(BookRepository repository,
                          BookMapper mapper,
                          AuthorRepository authorRepository) {
        super(repository, mapper);
        this.authorRepository = authorRepository;
    }

    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> booksPaginated = repository.findAll(pageable);
        List<BookDTO> result = mapper.toDTOs(booksPaginated.getContent());
        return new PageImpl<>(result, pageable, booksPaginated.getTotalElements());
    }

    public Page<BookDTO> searchBook(BookSearchDTO bookSearchDTO,
                                    Pageable pageRequest) {

        String genre = bookSearchDTO.getGenre() != null
                ? String.valueOf(bookSearchDTO.getGenre().ordinal())
                : null;

        Page<Book> booksPaginated = ((BookRepository) repository).searchBooks(
                bookSearchDTO.getBookTitle(),
                genre,
                bookSearchDTO.getAuthorName(),
                pageRequest
        );

        List<BookDTO> result = mapper.toDTOs(booksPaginated.getContent());
        return new PageImpl<>(result, pageRequest, booksPaginated.getTotalElements());

    }

    public BookDTO addAuthor(final Long bookId, final Long authorId) {
        BookDTO book = getOne(bookId);
        Author author = authorRepository.findById(authorId).orElseThrow(
                () -> new NotFoundException("Автор не найден"));
        book.getAuthorIds().add(author.getId());
        update(book);
        return book;
    }

    @Override
    public void deleteSoft(final Long id) throws MyDeleteException {
        Book book = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Книги не найдено"));
        boolean bookCanBeDeleted = ((BookRepository) repository).isBookCanBeDeleted(id);
        if (bookCanBeDeleted) {
            markAsDeleted(book);
            repository.save(book);
        } else {
            throw new MyDeleteException(Errors.Book.BOOK_DELETED_ERROR);
        }
    }

}
