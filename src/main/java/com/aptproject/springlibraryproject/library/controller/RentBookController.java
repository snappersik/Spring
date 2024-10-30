package com.aptproject.springlibraryproject.library.controller;

import com.aptproject.springlibraryproject.library.model.BookRentInfo;
import com.aptproject.springlibraryproject.library.repository.BookRentInfoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rent/info")
@Tag(name = "Аренда книг",
        description = "Контроллер для работы с арендой/выдачей книг пользователям библиотеки")
public class RentBookController
        extends GenericController<BookRentInfo>{
    public RentBookController(BookRentInfoRepository genericRepository) {
        super(genericRepository);
    }
}
