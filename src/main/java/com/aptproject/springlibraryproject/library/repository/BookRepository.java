package com.aptproject.springlibraryproject.library.repository;

import com.aptproject.springlibraryproject.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends GenericRepository<Book> {

    @Query(nativeQuery = true, value = """
                    SELECT DISTINCT b.*
                    FROM books b
                    LEFT JOIN books_authors ba ON b.id = ba.book_id
                    LEFT JOIN authors a ON a.id = ba.author_id
                    WHERE b.title ILIKE '%' || COALESCE(:title, '%') || '%'
                    AND CAST(b.genre AS CHAR) LIKE COALESCE(:genre, '%')
                    AND COALESCE(a.name, '%') ILIKE '%' || COALESCE(:name, '%') || '%'
                    AND b.is_deleted = false
                    """)
    Page<Book> searchBooks(
            @Param(value = "title") String bookTitle,
            @Param(value = "genre") String genre,
            @Param(value = "name") String authorName,
            Pageable pageRequest
    );
}
