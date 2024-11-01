package com.aptproject.springlibraryproject.library.service;

import com.aptproject.springlibraryproject.library.dto.RoleDTO;
import com.aptproject.springlibraryproject.library.dto.UserDTO;
import com.aptproject.springlibraryproject.library.mapper.GenericMapper;
import com.aptproject.springlibraryproject.library.model.User;
import com.aptproject.springlibraryproject.library.repository.GenericRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User, UserDTO>{
    public UserService(GenericRepository<User> repository,
                       GenericMapper<User, UserDTO> mapper) {
        super(repository, mapper);
    }

    @Override
    public UserDTO create(UserDTO newObject) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        newObject.setRole(roleDTO);
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }
}
