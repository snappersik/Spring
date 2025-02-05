package com.aptproject.springlibraryproject;

import com.aptproject.springlibraryproject.library.dto.GenericDTO;
import com.aptproject.springlibraryproject.library.exception.MyDeleteException;
import com.aptproject.springlibraryproject.library.mapper.GenericMapper;
import com.aptproject.springlibraryproject.library.model.GenericModel;
import com.aptproject.springlibraryproject.library.repository. GenericRepository;
import com.aptproject.springlibraryproject.library.service.GenericService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication. UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context. SecurityContextHolder;
import com.aptproject.springlibraryproject.library.service.userdetails.CustomUserDetails;

public abstract class GenericTest<E extends GenericModel, D extends GenericDTO> {

    protected GenericService<E, D> service;
    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;

    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                CustomUserDetails.builder()
                        .username("USER"),
                null,
                null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    protected abstract void getAll();
    protected abstract void getOne();
    protected abstract void create();
    protected abstract void update();
    protected abstract void delete() throws MyDeleteException;
    protected abstract void restore();
    protected abstract void getAllNotDeleted();

}