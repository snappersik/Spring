package com.aptproject.springlibraryproject.library.mapper;

import com.aptproject.springlibraryproject.library.dto.AuthorDTO;
import com.aptproject.springlibraryproject.library.dto.GenericDTO;
import com.aptproject.springlibraryproject.library.model.Author;
import com.aptproject.springlibraryproject.library.model.GenericModel;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
@Component
public abstract class GenericMapper <E extends GenericModel, D extends GenericDTO>
        implements Mapper<E, D> {

     private final Class<E> entityClass;
     private final Class<D> dtoClass;
     protected final ModelMapper modelMapper;

     public GenericMapper(Class<E> entityClass,
                          Class<D> dtoClass,
                          ModelMapper modelMapper) {
          this.entityClass = entityClass;
          this.dtoClass = dtoClass;
          this.modelMapper = modelMapper;
     }
     @Override
     public E toEntity(D dto) {
          return Objects.isNull(dto)
                  ? null
                  : modelMapper.map(dto, entityClass);
     }

     @Override
     public D toDTO(E entity) {
          return Objects.isNull(entity)
                  ? null
                  : modelMapper.map(entity, dtoClass);
     }

     @Override
     public List<E> toEntities(List<D> dtos) {
          return dtos.stream().map(this::toEntity).toList();
     }

     @Override
     public List<D> toDTOs(List<E> entities) {return entities.stream().map(this::toDTO).toList();}

     @PostConstruct
     protected abstract void setupMapper();

     protected abstract void mapSpecificFields(AuthorDTO source, Author destination);

     protected abstract void mapSpecificFields(Author source, AuthorDTO destination);

     protected abstract List<Long> getIds(E entity);

}