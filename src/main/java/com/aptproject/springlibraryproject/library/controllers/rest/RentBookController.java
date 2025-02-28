package com.aptproject.springlibraryproject.library.controllers.rest;


import com.aptproject.springlibraryproject.library.dto.BookRentInfoDTO;
import com.aptproject.springlibraryproject.library.model.BookRentInfo;
import com.aptproject.springlibraryproject.library.repository.BookRentInfoRepository;
import com.aptproject.springlibraryproject.library.service.BookRentInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/rent/info")
@Tag(name = "Аренда книг",
        description = "Контроллер для работы с арендой/выдачей книг пользователем библиотеки")
public class RentBookController
        extends GenericController<BookRentInfo, BookRentInfoDTO>{

    public RentBookController(BookRentInfoRepository genericRepository, BookRentInfoRepository bookRentInfoRepository, BookRentInfoService bookRentInfoService) {
        super(bookRentInfoService);
    }
}