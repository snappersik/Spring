package com.aptproject.springlibraryproject.library.service;


import com.aptproject.springlibraryproject.library.dto.BookRentInfoDTO;
import com.aptproject.springlibraryproject.library.mapper.BookRentInfoMapper;
import com.aptproject.springlibraryproject.library.model.BookRentInfo;
import com.aptproject.springlibraryproject.library.repository.BookRentInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class BookRentInfoService
        extends GenericService<BookRentInfo, BookRentInfoDTO>{
    protected BookRentInfoService(BookRentInfoRepository bookRentInfoRepository, BookRentInfoMapper bookRentInfoMapper) {
        super (bookRentInfoRepository, bookRentInfoMapper);
    }
}