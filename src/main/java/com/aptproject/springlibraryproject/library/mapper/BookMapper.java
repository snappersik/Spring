package com.aptproject.springlibraryproject.library.mapper;

import com.aptproject.springlibraryproject.library.dto.BookDTO;
import com.aptproject.springlibraryproject.library.model.Book;
import com.aptproject.springlibraryproject.library.model.GenericModel;
import com.aptproject.springlibraryproject.library.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BookMapper extends GenericMapper <Book, BookDTO> {
    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public BookMapper(ModelMapper modelMapper,
                      AuthorRepository authorRepository,
                      AuthorMapper authorMapper) {
        super(Book.class, BookDTO.class, modelMapper);
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(Book.class, BookDTO.class)
                .addMappings(mapping -> mapping.skip(BookDTO::setAuthorIds))
                .setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(BookDTO.class, Book.class)
                .addMappings(mapping -> mapping.skip(Book::setAuthors))
                .setPostConverter(toEntityConverter());
    }

    protected void mapSpecificFields(BookDTO source, Book destination) {
        if (!Objects.isNull(source.getAuthorIds())) {
            destination.setAuthors(authorRepository.findAllById(source.getAuthorIds()));
        } else
            destination.setAuthors(Collections.emptyList());
    }

    protected void mapSpecificFields(Book source, BookDTO destination) {
        destination.setAuthorIds(getIds(source));
        destination.setAuthorInfo(authorMapper.toDTOs(source.getAuthors()));
    }

    @Override
    protected List<Long> getIds(Book source) {
        return Objects.isNull(source) || Objects.isNull(source.getAuthors())
                ? null
                : source.getAuthors().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }
}
