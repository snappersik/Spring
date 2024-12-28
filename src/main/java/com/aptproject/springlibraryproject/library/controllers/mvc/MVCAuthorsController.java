package com.aptproject.springlibraryproject.library.controllers.mvc;


import com.aptproject.springlibraryproject.library.dto.AuthorDTO;
import com.aptproject.springlibraryproject.library.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/authors")
public class MVCAuthorsController {

    private final AuthorService authorService;

    public MVCAuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getAll(Model model) {
        List<AuthorDTO> authorDTOs = authorService.listAll();
        model.addAttribute("authors", authorDTOs);
        return "authors/view-all-authors";
    }

    @GetMapping("/add")
    public String create() {
        return "authors/add-author";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("authorForm") AuthorDTO newAuthor) {
        log.info(newAuthor.toString());
        authorService.create(newAuthor);
        return "redirect:/authors";
    }
}
