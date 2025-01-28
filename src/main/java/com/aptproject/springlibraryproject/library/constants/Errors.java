package com.aptproject.springlibraryproject.library.constants;

public interface Errors {

    class Book{
        public static final String BOOK_DELETED_ERROR = "Книга не может быть удаленна, т.к. имеются активные аренды";
    }
    class Author{
        public static final String AUTHOR_DELETED_ERROR = "Автор не может быть удален, т.к. имеются активные аренды";

    }
    class User{
        public static final String USER_DELETED_ERROR = "У вас нет прав просматривать информацию пользователей";

    }

    class REST{
        public static final String DELETED_ERROR_MASSGE = "Удаление невозможно";
        public static final String AUTH_ERROR_MASSGE = "Неавторизованный пользователь";
        public static final String ACCESS_ERROR_MASSGE ="Отказано в доступе";
        public static final String NOT_FOUND_ERROR_MASSGE ="Объект не найден";
    }
}

