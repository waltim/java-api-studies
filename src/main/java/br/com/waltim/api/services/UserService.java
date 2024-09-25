package br.com.waltim.api.services;

import br.com.waltim.api.domain.Users;
import br.com.waltim.api.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    Users findById(Long id);
    List<Users> findAll();
    Users create(UserDTO userDTO);
    Users update(UserDTO userDTO);
}
