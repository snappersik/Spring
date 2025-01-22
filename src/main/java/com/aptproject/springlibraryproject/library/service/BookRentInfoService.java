package com.aptproject.springlibraryproject.library.service;


import com.aptproject.springlibraryproject.library.dto.BookDTO;
import com.aptproject.springlibraryproject.library.dto.BookRentInfoDTO;
import com.aptproject.springlibraryproject.library.mapper.GenericMapper;
import com.aptproject.springlibraryproject.library.model.BookRentInfo;
import com.aptproject.springlibraryproject.library.repository.GenericRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookRentInfoService
        extends GenericService<BookRentInfo, BookRentInfoDTO> {

    private final BookService bookService;

    protected BookRentInfoService(GenericRepository<BookRentInfo> repository,
                                  GenericMapper<BookRentInfo, BookRentInfoDTO> mapper, BookService bookService) {
        super(repository, mapper);
        this.bookService = bookService;
    }

    public BookRentInfoDTO rentBook(final BookRentInfoDTO rentInfoDTO) {
        BookDTO bookDTO = bookService.getOne(rentInfoDTO.getBookId());
        bookDTO.setAmount(bookDTO.getAmount() - 1);
        bookService.update(bookDTO);

        long rentPeriod = rentInfoDTO.getRentPeriod() != null ? rentInfoDTO.getRentPeriod() : 14L;
        rentInfoDTO.setRentDate(LocalDateTime.now());
        rentInfoDTO.setReturned(false);
        rentInfoDTO.setRentPeriod((int) rentPeriod);
        rentInfoDTO.setReturnDate(LocalDateTime.now().plusDays(rentPeriod));
        rentInfoDTO.setCreatedWhen(LocalDateTime.now());
        rentInfoDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        return mapper.toDTO(repository.save(mapper.toEntity(rentInfoDTO)));
    }

}

