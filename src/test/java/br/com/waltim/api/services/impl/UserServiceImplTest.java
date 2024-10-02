package br.com.waltim.api.services.impl;

import br.com.waltim.api.domain.Users;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.domain.vo.Address;
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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class UserServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "UserTest";
    public static final String EMAIL = "teste@teste.com";
    public static final String PASSWORD = "123321";
    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    public static final int INDEX = 0;
    public static final String EMAIL_JA_CADASTRO_NO_SISTEMA = "Email já cadastro no sistema.";

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
    void shouldReturnUserWhenFoundById() {
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
    void shouldThrowObjectNotFoundExceptionWhenUserNotFoundById() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));

        try {
            service.findById(ID);
        } catch (ObjectNotFoundException ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(USUARIO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void shouldReturnListOfUsersWhenFindAll() {
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
    void shouldReturnSuccessWhenCreatingUser() {
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
    void shouldThrowDataIntegrityViolationExceptionWhenCreatingNewUserWithDuplicateEmail() {
        when(repository.findByEmail(anyString())).thenReturn(userOptional);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userOptional.get().setId(1L);
            userDTO.setId(null);
            service.create(userDTO);
        });
        assertEquals(EMAIL_JA_CADASTRO_NO_SISTEMA, exception.getMessage());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
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
//    whenUpdateThenReturnDataIntegrityViolationException
    void shouldReturnDataIntegrityViolationExceptionWhenUpdatingUserWithDuplicateEmail() {
        when(repository.findByEmail(anyString())).thenReturn(userOptional);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userOptional.get().setId(2L);
            service.update(userDTO);
        });

        assertEquals(EMAIL_JA_CADASTRO_NO_SISTEMA, exception.getMessage());
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        when(repository.findById(anyLong())).thenReturn(userOptional);
        doNothing().when(repository).deleteById(anyLong());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingNonExistentUser() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Usuário não encontrado"));
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(ID));
        assertEquals("Usuário não encontrado",
                exception.getMessage());
    }

    private void startUser() {
        Address address = new Address("Main Street", "123", "Apt 4B", "Springfield", "IL", "USA");
        user = new Users(ID, NAME, EMAIL, address, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, address, PASSWORD);
        userOptional = Optional.of(new Users(ID, NAME, EMAIL, address, PASSWORD));
    }
}