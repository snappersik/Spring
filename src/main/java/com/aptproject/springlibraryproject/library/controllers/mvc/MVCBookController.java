package com.aptproject.springlibraryproject.library.controllers.mvc;

import com.aptproject.springlibraryproject.library.dto.BookDTO;
import com.aptproject.springlibraryproject.library.service.BookService;
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
@RequestMapping("/books")
public class MVCBookController {
    private final BookService bookService;

    public MVCBookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public String getAll(Model model){
        List<BookDTO> bookDTOs = bookService.listAll();
        model.addAttribute("books", bookDTOs);
        return "books/view-all-books";
    }

    @GetMapping("/add")
    public  String create() {
        return "books/add-book";
    }

    @PostMapping("/add")
    public  String create(@ModelAttribute ("BookForm") BookDTO newBook) {
        log.info(newBook.toString());
        bookService.create(newBook);
        return "redirect:/books";
    }


}
