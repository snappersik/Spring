package com.aptproject.springlibraryproject.library.service;


import com.aptproject.springlibraryproject.library.dto.BookDTO;
import com.aptproject.springlibraryproject.library.dto.BookRentInfoDTO;
import com.aptproject.springlibraryproject.library.mapper.GenericMapper;
import com.aptproject.springlibraryproject.library.model.BookRentInfo;
import com.aptproject.springlibraryproject.library.repository.BookRentInfoRepository;
import com.aptproject.springlibraryproject.library.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public Page<BookRentInfoDTO> listUserRentBooks(final Long id, final Pageable pageRequest) {
        Page<BookRentInfo> objects = ((BookRentInfoRepository) repository).getBookRentInfoByUserId(id, pageRequest);
        List<BookRentInfoDTO> results = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(results, pageRequest, objects.getTotalElements());
    }

    public void returnBook(final Long id) {
        BookRentInfoDTO bookRentInfoDTO = getOne(id);
        bookRentInfoDTO.setReturned(true);
        bookRentInfoDTO.setReturnDate(LocalDateTime.now());
        BookDTO bookDTO = bookRentInfoDTO.getBookDTO();
        bookDTO.setAmount(bookDTO.getAmount() + 1);
        update(bookRentInfoDTO);
        bookService.update(bookDTO);
    }

}

