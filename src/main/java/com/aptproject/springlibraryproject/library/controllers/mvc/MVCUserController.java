package com.aptproject.springlibraryproject.library.controllers.mvc;

import com.aptproject.springlibraryproject.library.dto.UserDTO;
import com.aptproject.springlibraryproject.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.aptproject.springlibraryproject.library.constants.UserRoleConstants.ADMIN;

@Controller
@Slf4j
@RequestMapping("/users")
public class MVCUserController {
    private final UserService userService;

    public MVCUserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm")UserDTO userDTO,
                               BindingResult bindingResult){
        if (userDTO.getLogin().equalsIgnoreCase(ADMIN) || userService.getUserByLogin(userDTO.getLogin()) != null){
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "registration";
        }
        if (userService.getUserByEmail(userDTO.getEmail()) != null){
            bindingResult.rejectValue("email", "error.email", "Такой e-mail уже существует");
        }
        userService.create(userDTO);
        return "redirect:/login";

//        email
    }

}
