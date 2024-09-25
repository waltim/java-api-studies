package br.com.waltim.api.services.impl;

import br.com.waltim.api.domain.Users;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.repositories.UserRepository;

import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class UserServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "UserTest";
    public static final String EMAIL = "teste@teste.com";
    public static final String PASSWORD = "123321";
    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    public static final int INDEX = 0;

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    private Users user;
    private UserDTO userDTO;
    private Optional<Users> userOptional;

    @BeforeEach
    void setUp() {
        openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUser() {
        when(repository.findById(anyLong())).thenReturn(userOptional);
        Users response = service.findById(ID);

        assertNotNull(response);

        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));

        try {
            service.findById(ID);
        } catch (ObjectNotFoundException ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(USUARIO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<Users> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Users.class, response.getFirst().getClass());

        assertEquals(ID, response.getFirst().getId());
        assertEquals(NAME, response.getFirst().getName());
        assertEquals(EMAIL, response.getFirst().getEmail());
        assertEquals(PASSWORD, response.getFirst().getPassword());

    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        Users response = service.create(userDTO);

        assertEquals(Users.class, response.getClass());
        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(userOptional);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userOptional.get().setId(1L);
            service.create(userDTO);
        });
        assertEquals("Email já cadastro no sistema.", exception.getMessage());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        Users response = service.update(userDTO);

        assertEquals(Users.class, response.getClass());
        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(userOptional);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userOptional.get().setId(2L);
            service.update(userDTO);
        });

        assertEquals("Email já cadastro no sistema.", exception.getMessage());
    }

    @Test
    void delete() {
    }

    private void startUser() {
        user = new Users(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(null, NAME, EMAIL, PASSWORD);
        userOptional = Optional.of(new Users(ID, NAME, EMAIL, PASSWORD));
    }
}