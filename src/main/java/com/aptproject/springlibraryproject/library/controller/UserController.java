package com.aptproject.springlibraryproject.library.controller;

import com.aptproject.springlibraryproject.library.model.User;
import com.aptproject.springlibraryproject.library.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями библиотеки")
public class UserController
        extends GenericController<User>{
    public UserController(UserRepository userRepository) {
        super(userRepository);
    }
}
