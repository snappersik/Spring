package com.aptproject.springlibraryproject.library.controllers.mvc;


import com.aptproject.springlibraryproject.library.dto.AuthorDTO;
import com.aptproject.springlibraryproject.library.dto.AddBookDTO;
import com.aptproject.springlibraryproject.library.exception.MyDeleteException;
import com.aptproject.springlibraryproject.library.service.AuthorService;
import com.aptproject.springlibraryproject.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/authors")
@Slf4j
public class MVCAuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public MVCAuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "authorName"));
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<AuthorDTO> result;

        if ("ADMIN".equalsIgnoreCase(userName)) {
            result = authorService.listAll(pageRequest);
        } else {
            result = authorService.listAllNotDeleted(pageRequest);
        }

        model.addAttribute("authors", result);
        return "authors/view-all-authors";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.getOne(id));
        return "authors/view-author";
    }

    @GetMapping("/add")
    public String create() {
        return "authors/add-author";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("authorForm") AuthorDTO authorDTO) {
        authorService.create(authorDTO);
        return "redirect:/authors";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.getOne(id));
        return "authors/update-author";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("authorForm") AuthorDTO authorDTO) {
        authorService.update(authorDTO);
        return "redirect:/authors";
    }

    @GetMapping("/add-book/{authorId}")
    public String addBook(@PathVariable Long authorId, Model model) {
        model.addAttribute("books", bookService.listAll());
        model.addAttribute("authorId", authorId);
        model.addAttribute("author", authorService.getOne(authorId).getAuthorName());
        return "authors/add-author-book";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute("authorBookForm") AddBookDTO addBookDTO) {
        authorService.addBook(addBookDTO);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        authorService.deleteSoft(id);
        return "redirect:/authors";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        authorService.restore(id);
        return "redirect:/authors";
    }

    @PostMapping("/search")
    public String searchAuthors(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "5") int pageSize,
                                @ModelAttribute("authorSearchForm") AuthorDTO authorDTO,
                                Model model) {
        if (StringUtils.hasText(authorDTO.getAuthorName()) || StringUtils.hasLength(authorDTO.getAuthorName())) {
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "authorName"));
            model.addAttribute("authors", authorService.searchAuthors(authorDTO.getAuthorName().trim(), pageRequest));
            return "authors/view-all-authors";
        } else {
            return "redirect:/authors";
        }
    }
}
