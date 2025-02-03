package com.aptproject.springlibraryproject.library.service;

import com.aptproject.springlibraryproject.library.dto.AuthorDTO;
import com.aptproject.springlibraryproject.library.dto.AddBookDTO;
import com.aptproject.springlibraryproject.library.exception.MyDeleteException;
import com.aptproject.springlibraryproject.library.model.Author;
import com. aptproject.springlibraryproject.library.model.Book;
import com.aptproject.springlibraryproject.library.mapper.AuthorMapper;
import com.aptproject.springlibraryproject.library.repository.AuthorRepository;
import com.aptproject.springlibraryproject.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import com.aptproject.springlibraryproject.library.constants.Errors;

import java.util.List;

@Service
public class AuthorService extends GenericService<Author, AuthorDTO> {


    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper, BookRepository bookRepository) {
        super(authorRepository, authorMapper);
    }

    public AuthorDTO addBook(final AddBookDTO addBookDTO) {
        AuthorDTO author = getOne(addBookDTO.getAuthorId());
        author.getBooksIds().add(addBookDTO.getBookId());
        update(author);
        return author;
    }

    public Page<AuthorDTO> searchAuthors(final String name, Pageable pageable) {
        Page<Author> authors = ((AuthorRepository) repository).findAllByAuthorNameContainsIgnoreCaseAndIsDeletedFalse(name, pageable);
        List<AuthorDTO> result = mapper.toDTOs(authors.getContent());
        return new PageImpl<>(result, pageable, authors.getTotalElements());
    }

//    SOFT DELETED

    @Override
    public void deleteSoft(Long objectId) throws MyDeleteException {
        Author author = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Автора с заданным id=" + objectId + " не существует."));
        boolean authorCanBeDeleted = ((AuthorRepository)repository).checkAuthorForDeletion(objectId);
        if (authorCanBeDeleted) {
            markAsDeleted(author);
            List<Book> books = author.getBooks();
            if (books != null && books.size() > 0) {
                books.forEach(this::markAsDeleted);
            }
            ((AuthorRepository) repository).save(author);
        } else {
            throw new MyDeleteException(Errors.Authors.AUTHOR_DELETED_ERROR);
        }
    }

    public void restore(Long objectId) {
        Author author = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Автора с заданным id=" + objectId + " не существует."));
        unMarkAsDeleted(author);
        List<Book> books = author.getBooks();
        if (books != null && books.size() > 0) {
            books.forEach(this::unMarkAsDeleted);
        }
        repository.save(author);
    }

}
