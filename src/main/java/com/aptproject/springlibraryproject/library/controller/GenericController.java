package com.aptproject.springlibraryproject.library.controller;

import com.aptproject.springlibraryproject.library.model.GenericModel;
import com.aptproject.springlibraryproject.library.repository.GenericRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j

public abstract class GenericController <T extends GenericModel> { // Ограничиваем работу только с моделями
    // которые наследуются от GenericModel

    private final GenericRepository<T> genericRepository;

    protected GenericController(GenericRepository<T> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Operation(description = "Получить запись по Id", method = "getOneById")
    @RequestMapping(value = "/getOneById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getOneById(@RequestParam(value = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(genericRepository.findById(id).orElseThrow(() -> new NotFoundException("Данные по заданному ID не найдены")));
    }

    @Operation(description = "Получить все записи", method = "getAll")
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE) // сразу указали тип маппинга "get"

    public ResponseEntity<List<T>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(genericRepository.findAll());
    }

    @Operation(description = "Создать запись", method = "create")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> create(@RequestBody T newEntity) {
        newEntity.setCreateWhen(LocalDate.now());
        log.info(newEntity.toString()); // стал доступен благодаря @Slf4j
        genericRepository.save(newEntity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newEntity);
    }

    @Operation(description = "Обновить запись", method = "update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<T> update(@RequestBody T updateEntity, @RequestParam(value = "id") Long id) {
        updateEntity.setId(id);
        genericRepository.save(updateEntity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updateEntity);
    }

    // localhost:8080/authors/delete?id=1 - @RequestParam
    // localhost: 8080/authors/delete/1 - @PathVariable
    @Operation(description = "Удалить запись", method = "delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        genericRepository.deleteById(id);
    }
}