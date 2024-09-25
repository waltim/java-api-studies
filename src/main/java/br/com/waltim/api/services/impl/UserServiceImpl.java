package br.com.waltim.api.services.impl;

import br.com.waltim.api.domain.Users;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.repositories.UserRepository;
import br.com.waltim.api.services.UserService;
import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Users findById(Long id) {
        Optional<Users> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    @Override
    public List<Users> findAll() {
        return repository.findAll();
    }

    @Override
    public Users create(UserDTO userDTO) {
        findByEmail(userDTO);
        return repository.save(mapper.map(userDTO, Users.class));
    }

    private void findByEmail(UserDTO userDTO){
        Optional<Users> user = repository.findByEmail(userDTO.getEmail());
        if (user.isPresent()) {
            throw new DataIntegrityViolationException("Usuário já cadastro no banco.");
        }
    }

}
