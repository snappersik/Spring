package com.aptproject.springlibraryproject.library.controllers.mvc;

import com.aptproject.springlibraryproject.library.dto.AuthorDTO;
import com.aptproject.springlibraryproject.library.dto.BookDTO;
import com.aptproject.springlibraryproject.library.dto.BookSearchDTO;
import com.aptproject.springlibraryproject.library.exception.MyDeleteException;
import com.aptproject.springlibraryproject.library.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/books")
public class MVCBookController {

    private final BookService bookService;

    public MVCBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC,
                "bookTitle"));
        Page<BookDTO> books = bookService.getAllBooks(pageRequest);
        model.addAttribute("books", books);
        return "books/view-all-books";
    }

    @PostMapping("/search")
    public String searchBooks(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            @ModelAttribute("bookSearchForm") BookSearchDTO bookSearchDTO,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC,
                "title"));
        model.addAttribute("books", bookService.searchBook(bookSearchDTO, pageRequest));
        return "books/view-all-books";
    }

    @GetMapping("/add")
    public String create() {
        return "books/add-book";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("bookForm") BookDTO newBook,
                         @RequestParam("onlineCopy") MultipartFile file) {
        log.info(newBook.toString());

        if (file != null && file.getSize() > 0) {
            log.info(file.getName());
            bookService.create(newBook, file);
        } else {
            bookService.create(newBook);
        }

        return "redirect:/books";
    }


    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getOne(id));
        return "books/view-book";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getOne(id));
        return "books/updateBook";
    }

    @PostMapping
    public String update(@ModelAttribute("bookForm") BookDTO bookDTO) {
        bookService.update(bookDTO);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        bookService.deleteSoft(id);
        return "redirect:/books";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        bookService.restore(id);
        return "redirect:/books";
    }

    /**
     * Метод для поиска книги по ФИО автора (редирект по кнопке "Посмотреть книги" на странице автора).
     *
     * @param page      - текущая страница
     * @param pageSize  - количество объектов на странице
     * @param authorDTO - ДТО автора
     * @param model     - модель
     * @return форма со списком всех книг, подходящих под критерии (по фио автора)
     */

    @PostMapping("/search/books-by-author")
    public String searchBooks(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int pageSize,
                              @ModelAttribute("authorSearchForm") AuthorDTO authorDTO,
                              Model model) {
        BookSearchDTO bookSearchDTO = new BookSearchDTO();
        bookSearchDTO.setAuthorName(authorDTO.getAuthorName());
        return searchBooks(page, pageSize, bookSearchDTO, model);
    }

    @GetMapping(value = "/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadBook(@RequestParam(value = "bookId") Long bookId) throws IOException {
        BookDTO bookDTO = bookService.getOne(bookId);
        Path path = Paths.get(bookDTO.getOnlineCopyPath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(createHeaders(path.getFileName().toString()))
                .contentLength(path.toFile().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private HttpHeaders createHeaders(final String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        headers.add("Cache-Control", "no-cache, no-store");
        headers.add("Expires", "0");
        return headers;
    }

    @ExceptionHandler({MyDeleteException.class, AccessDeniedException.class, NotFoundException.class})
    public RedirectView handleError(HttpServletRequest request,
                                    Exception exception,
                                    RedirectAttributes redirectAttributes) {
        log.error("Запрос {} вызвал ошибку: {}", request.getRequestURL(), exception.getMessage());
        redirectAttributes.addFlashAttribute("exception", exception.getMessage());
        return new RedirectView("/books", true);
    }


}