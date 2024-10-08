package br.com.waltim.api.services.impl;

import br.com.waltim.api.domain.User;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.repositories.UserRepository;
import br.com.waltim.api.resources.UserResource;
import br.com.waltim.api.services.UserService;
import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = repository.findById(id);
        UserDTO userDTO = mapper.map(user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado")), UserDTO.class);
        // Adicionando o link HATEOAS para o próprio recurso
        userDTO.add(linkTo(methodOn(UserResource.class).findById(id)).withSelfRel());

        // Adicionar outros links conforme necessário, por exemplo, link para listar todos os usuários
        userDTO.add(linkTo(methodOn(UserResource.class).findAll()).withRel("all-users"));

        return userDTO;
    }

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll().stream()
                .map(u -> {
                    UserDTO userDTO = mapper.map(u, UserDTO.class);
                    // Adicionando o link HATEOAS para o próprio recurso
                    userDTO.add(linkTo(methodOn(UserResource.class).findById(userDTO.getKey())).withSelfRel());
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        findByEmail(userDTO);
        UserDTO user = mapper.map(repository.save(mapper.map(userDTO, User.class)), UserDTO.class);
        user.add(linkTo(methodOn(UserResource.class).findById(userDTO.getKey())).withSelfRel());
        return user;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        findByEmail(userDTO);
        findById(userDTO.getKey());
        UserDTO user = mapper.map(repository.save(mapper.map(userDTO, User.class)), UserDTO.class);
        user.add(linkTo(methodOn(UserResource.class).findById(userDTO.getKey())).withSelfRel());
        return user;
    }

    @Override
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmail(UserDTO userDTO) {
        Optional<User> user = repository.findByEmail(userDTO.getEmail());
        if (user.isPresent() && (userDTO.getKey() == null || !user.get().getKey().equals(userDTO.getKey()))) {
            throw new DataIntegrityViolationException("Email já cadastro no sistema.");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username not found.");
        }
    }
}
