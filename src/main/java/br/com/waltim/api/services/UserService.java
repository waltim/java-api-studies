package br.com.waltim.api.services;

import br.com.waltim.api.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    UserDTO create(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    void delete(Long id);
}
