package com.aptproject.springlibraryproject.service;

import com.aptproject.springlibraryproject.library.dto.AddBookDTO;
import com.aptproject.springlibraryproject.library.dto.AuthorDTO;
import com.aptproject.springlibraryproject.library.exception.MyDeleteException;
import com.aptproject.springlibraryproject.library.mapper.AuthorMapper;
import com.aptproject.springlibraryproject.library.model.Author;
import com.aptproject.springlibraryproject.library.repository.AuthorRepository;
import com.aptproject.springlibraryproject.library.service. AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org. mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util. List;
import java.util. Optional;
import java.util.function. Predicate;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Очередность тестов. Тесты могут быть чувствительны к порядку
public class AuthorServiceTest
        extends GenericTest<Author, AuthorDTO> {

    public AuthorServiceTest() {
        super();
        repository = Mockito.mock(AuthorRepository.class); // mock - имитация pепозитория,
        // если метод заменить на spy, то поднимется реальный репозиторий
        mapper = Mockito.mock(AuthorMapper.class);
        service = new AuthorService((AuthorRepository) repository, (AuthorMapper) mapper);

        AuthorTestData.AUTHOR_1.setDeleted(false);
        AuthorTestData.AUTHOR_2.setDeleted(false);
        AuthorTestData.AUTHOR_3.setDeleted(false);
    }
        @Test
        @Order(1)
        @Override
                Mockito.when(repository.findAll()).thenReturn(AuthorTestData.AUTHOR_LIST);
                Mockito.when(mapper.toDTOs(AuthorTestData.AUTHOR_LIST)).thenReturn(AuthorTestData.AUTHOR_DTO_LIST);

                List<AuthorDTO> authorDTOS = service.listAll();
                log.info("Testing getAll(): {}", authorDTOS);
                assertEquals(AuthorTestData.AUTHOR_LIST.size(), authorDTOS.size());
            }

            @Test
            @Order(2)
            @Override
            protected void getOne() {
                Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
                Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);

                AuthorDTO authorDTO = service.getOne(1L);
                log.info("Testing getOne(): {}", authorDTO);
                assertSame(AuthorTestData.AUTHOR_DTO_1, authorDTO); // Используем assertSame() для проверки ссылок на один и тот же объект.
            }

            @Test
            @Order(3)
            @Override
            protected void create() {
                Mockito.when(mapper.toEntity(AuthorTestData.AUTHOR_DTO_1)).thenReturn(AuthorTestData.AUTHOR_1);
                Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
                Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);

                AuthorDTO authorDTO = service.create(AuthorTestData.AUTHOR_DTO_1);
                log.info("Testing create(): {}", authorDTO);
            }

            @Test
            @Order(4)
            @Override
            protected void update() {
                Mockito.when(mapper.toEntity(AuthorTestData.AUTHOR_DTO_1)).thenReturn(AuthorTestData.AUTHOR_1);
                Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
                Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);

                AuthorDTO authorDTO = service.update(AuthorTestData.AUTHOR_DTO_1);
                log.info("Testing update(): {}", authorDTO);
            }

            @Test
            @Order(5)
            @Override
            protected void delete() throws MyDeleteException {
                Mockito.when(((AuthorRepository) repository).checkAuthorForDeletion(1L)).thenReturn(true);
                Mockito.when(((AuthorRepository) repository).checkAuthorForDeletion(2L)).thenReturn(false);

                Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));

                service.deleteSoft(1L);
                assertTrue(AuthorTestData.AUTHOR_1.isDeleted());
            }
        }


    }
