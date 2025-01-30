package com.aptproject.springlibraryproject.library.constants;

import java.util.List;

public interface SecurityConstants {
    List<String> RESOURCES_WHITE_LIST = List.of("/resources/**",
            "/static/**",
            "/js/**",
            "/css/**",
            "/",
            "/swagger-ui/**",
            "/webjars/bootstrap/5.3.3/**",
            "/v3/api-docs/**");

    List<String> BOOKS_WHITE_LIST = List.of(
            "/books/search",
            "/books/{id}",
            "/books/{id}");

    List<String> AUTHORS_WHITE_LIST =List.of(
            "/authors",
            "/authors/search",
            "/authors/search/books-by-authors",
            "/authors/{id}");

    List<String> BOOKS_PERMISSIONS_LIST = List.of("/books/add",
            "/books/update",
            "/books/delete",
            "/books/download/{bookId}");

    List<String> AUTHORS_PERMISSIONS_LIST = List.of("/authors/add",
            "authors/update",
            "authors/delete"
    );

    List<String> USERS_WHITE_LIST = List.of("/login",
            "/users/registration",
            "/users/remember-password",
            "/users/change-password");

    List<String> USER_PERMISSION_LIST = List.of("/rent/book/*");
    List<String> USERS_REST_WHITE_LIST = List.of("/users/auth");

}