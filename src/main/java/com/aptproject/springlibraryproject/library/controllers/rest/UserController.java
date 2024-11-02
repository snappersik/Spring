package com.aptproject.springlibraryproject.library.controllers.rest;


import com.aptproject.springlibraryproject.library.dto.UserDTO;
import com.aptproject.springlibraryproject.library.model.User;
import com.aptproject.springlibraryproject.library.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи",description = "Контроллер для работы с пользователями библиотеки")
public class UserController
        extends GenericController<User, UserDTO>{
    public UserController(UserService userService) {
        super(userService);
    }
}