package com.aptproject.springlibraryproject.library.controllers.mvc;

import com.aptproject.springlibraryproject.library.constants.Errors;
import com.aptproject.springlibraryproject.library.dto.UserDTO;
import com.aptproject.springlibraryproject.library.model.User;
import com.aptproject.springlibraryproject.library.service.UserService;
import com.aptproject.springlibraryproject.library.service.userdetails.CustomUserDetails;
import jakarta.security.auth.message.AuthException;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.aptproject.springlibraryproject.library.constants.UserRoleConstants.ADMIN;

@Controller
@Slf4j
@RequestMapping("/users")
public class MVCUserController {

    private final UserService userService;

    public MVCUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }

    @GetMapping("/list")
    public String ListAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "10") int pageSize,
                               Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "login"));
        Page<UserDTO> userPage = userService.listAll(pageRequest);
        model.addAttribute("users", userPage);
        return "users/view-all-users";
    }

    @GetMapping("/add-librarian")
    public String addLibrarian(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "users/add-librarian";
    }

    @PostMapping("/add-librarian")
    public String addLibrarianPage(@ModelAttribute("userForm") UserDTO userDTO, BindingResult bindingResult) {
        if (userDTO.getLogin().equalsIgnoreCase(ADMIN) || userService.getUserByLogin(userDTO.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "registration";
        }
        if (userService.getUserByEmail(userDTO.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такой email уже существует");
            return "registration";
        }
        userService.createLibrarian(userDTO);
        return "redirect:/users/list";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDTO userDTO,
                               BindingResult bindingResult) {
        if (userDTO.getLogin().equalsIgnoreCase(ADMIN) || userService.getUserByLogin(userDTO.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "registration";
        }
        if (userService.getUserByEmail(userDTO.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такой e-mail уже существует");
            return "registration";
        }
        userService.create(userDTO);
        return "redirect:/login";
    }

    @GetMapping("/remember-password")
    public String rememberPassword () {
        return "users/remember-password";
    }

    @PostMapping("/remember-password")
    public String rememberPassword(@ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userDTO = userService.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userDTO)) {
            return "Ошибка!";
        } else {
            userService.sendChangePasswordEmail(userDTO);
            return "redirect:/login";
        }

    }

    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 Model model) {
        model.addAttribute("uuid", uuid);
        return "users/change-password";

    }

    @PostMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 @ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userService.changePassword(uuid, userDTO.getPassword());
        return "redirect:/login";

    }

    @GetMapping("/profile/{id}")
    public String getUserProfile(@PathVariable Integer id,
                                 @ModelAttribute(value = "exception") String exception,
                                 Model model) throws AuthException{
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (!Objects.isNull((customUserDetails.getUserId()))) {
            if (!ADMIN.equalsIgnoreCase(customUserDetails.getUsername())) {
                if (!id.equals(customUserDetails.getUserId())) {
                    throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Users.USER_FORBIDDEN_ERROR);
                }
            }
        }

        model.addAttribute("user", userService.getOne(Long.valueOf(id)));
        model.addAttribute("exception", exception);

        return "profile/view-profile";

    }

}