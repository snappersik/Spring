package com.aptproject.springlibraryproject.library.service;

import com.aptproject.springlibraryproject.library.dto.GenericDTO;
import com.aptproject.springlibraryproject.library.mapper.GenericMapper;
import com.aptproject.springlibraryproject.library.model.GenericModel;
import com.aptproject.springlibraryproject.library.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Абстрактный сервис который хранит в себе реализацию CRUD операций по умолчанию
 * Если реализация отличная от того что представлено в этом классе,
 * то она переопределяется в сервисе для конкретной сущности
 *
 * @param <E> - Сущность с которой мы работаем
 * @param <D> - DTO, которую мы будем отдавать/принимать дальше
 */
@Service
public abstract class GenericService<E extends GenericModel, D extends GenericDTO> {

    protected final GenericRepository<E> repository;
    protected final GenericMapper<E, D> mapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GenericService(GenericRepository<E> repository, GenericMapper<E, D> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<D> listAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public Page<D> listAll(Pageable pageable) {
        Page<E> objects = repository.findAll(pageable);
        List<D> result = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(result, pageable, objects.getTotalElements());
    }

    public D getOne(final Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Данные по заданному id:" + id + " не найдено!")));
    }

    public D create(D newObject) {
        newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }

    public D update(D updatedObject) {
        return mapper.toDTO(repository.save(mapper.toEntity(updatedObject)));
    }


    public void delete(final Long id) {
        repository.deleteById(id);
    }
}
