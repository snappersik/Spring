package com.aptproject.springlibraryproject.library.service;

import com.aptproject.springlibraryproject.library.dto.RoleDTO;
import com.aptproject.springlibraryproject.library.dto.UserDTO;
import com.aptproject.springlibraryproject.library.mapper.GenericMapper;
import com.aptproject.springlibraryproject.library.model.User;
import com.aptproject.springlibraryproject.library.repository.GenericRepository;
import com.aptproject.springlibraryproject.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService extends GenericService<User, UserDTO> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(GenericRepository<User> repository,
                       GenericMapper<User, UserDTO> mapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository, mapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO create(UserDTO newObject) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        newObject.setRole(roleDTO);
        newObject.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));
        newObject.setCreatedBy("REGISTRATION FORM");
        newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }

    public UserDTO getUserByLogin(final String login) {
        return mapper.toDTO(((UserRepository) repository).findUserByLogin(login));
    }

    public UserDTO getUserByEmail(final String email) {
        return mapper.toDTO(((UserRepository) repository).findUserByLogin(email));
    }

    public boolean checkPassword(String password, UserDetails foundUser) {
        return bCryptPasswordEncoder.matches(password, foundUser.getPassword());
    }
}
