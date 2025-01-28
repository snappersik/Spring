package com.aptproject.springlibraryproject.library.controllers.mvc;

import com.aptproject.springlibraryproject.library.dto.BookRentInfoDTO;
import com.aptproject.springlibraryproject.library.service.BookRentInfoService;
import com.aptproject.springlibraryproject.library.service.BookService;
import com.aptproject.springlibraryproject.library.service.userdetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rent")
public class MVCBookRentInfoController {

    private final BookRentInfoService bookRentInfoService;
    private final BookService bookService;

    public MVCBookRentInfoController(BookRentInfoService bookRentInfoService, BookService bookService) {
        this.bookRentInfoService = bookRentInfoService;
        this.bookService = bookService;
    }

    @GetMapping("/book/{bookId}")
    public String rentBook(
            @PathVariable Long bookId,
            Model model
    ) {
        model.addAttribute("book", bookService.getOne(bookId));
        return "userBooks/rent-book";
    }

    @PostMapping("/book")
    public String rentBook(@ModelAttribute("rentBookInfo")BookRentInfoDTO bookRentInfoDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        bookRentInfoDTO.setUserId(Long.valueOf(customUserDetails.getUserId()));
        bookRentInfoService.rentBook(bookRentInfoDTO);
        return "redirect:/rent/user-books/" + customUserDetails.getUserId();
    }

    @GetMapping("/return-book/{id}")
    public String returnBook(@PathVariable Long id) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookRentInfoService.returnBook(id);
        return "redirect:/rent/user-books/" + customUserDetails.getUserId();
    }


    @GetMapping("/user-books/{id}")
    public String getUserBooks(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            @PathVariable Long id, Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<BookRentInfoDTO> bookRentInfoDTOPage = bookRentInfoService.listAll(pageRequest);
        model.addAttribute("rentBooks", bookRentInfoDTOPage);
        return "/userBooks/view-all-users-books";
    }

}