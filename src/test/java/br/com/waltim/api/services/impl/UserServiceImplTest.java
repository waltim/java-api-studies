package br.com.waltim.api.services.impl;

import br.com.waltim.api.domain.Users;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.domain.vo.Address;
import br.com.waltim.api.repositories.UserRepository;

import br.com.waltim.api.services.UserService;
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
import java.util.stream.Collectors;

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
    public static final String STREET = "Main Street";
    public static final String NUMBER = "123";
    public static final String COMPLEMENT = "Apt 4B";
    public static final String CITY = "Springfield";
    public static final String STATE = "IL";
    public static final String COUNTRY = "USA";

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
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO response = service.findById(ID);

        assertNotNull(response);

        assertEquals(UserDTO.class, response.getClass());
        assertEquals(ID, response.getKey());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertNotSame(PASSWORD, response.getPassword());

        assertEquals(STREET,response.getAddress().getStreet());
        assertEquals(NUMBER,response.getAddress().getNumber());
        assertEquals(COMPLEMENT,response.getAddress().getComplement());
        assertEquals(CITY,response.getAddress().getCity());
        assertEquals(STATE,response.getAddress().getState());
        assertEquals(COUNTRY,response.getAddress().getCountry());

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
        List<Users> users = List.of(user); // Cria uma lista com a entidade
        when(repository.findAll()).thenReturn(users); // Mock do repositório para retornar a lista de entidades
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO); // Mock do mapper para converter entidade em DTO

        List<UserDTO> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserDTO.class, response.getFirst().getClass());

        assertEquals(ID, response.getFirst().getKey());
        assertEquals(NAME, response.getFirst().getName());
        assertEquals(EMAIL, response.getFirst().getEmail());
        assertNotSame(PASSWORD, response.getFirst().getPassword());

        assertEquals(STREET,response.getFirst().getAddress().getStreet());
        assertEquals(NUMBER,response.getFirst().getAddress().getNumber());
        assertEquals(COMPLEMENT,response.getFirst().getAddress().getComplement());
        assertEquals(CITY,response.getFirst().getAddress().getCity());
        assertEquals(STATE,response.getFirst().getAddress().getState());
        assertEquals(COUNTRY,response.getFirst().getAddress().getCountry());

    }

    @Test
    void shouldReturnSuccessWhenCreatingUser() {
        when(mapper.map(userDTO, Users.class)).thenReturn(user); // Mock do mapeamento de DTO para Users
        when(repository.save(any())).thenReturn(user); // Mock do repositório para salvar o usuário
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO); // Mock do mapeamento de Users para DTO

        // Act
        UserDTO response = service.create(userDTO);

        assertEquals(UserDTO.class, response.getClass());
        assertNotNull(response);
        assertEquals(ID, response.getKey());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertNotSame(PASSWORD, response.getPassword());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenCreatingNewUserWithDuplicateEmail() {
        when(repository.findByEmail(anyString())).thenReturn(userOptional);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userOptional.get().setKey(1L);
            userDTO.setKey(null);
            service.create(userDTO);
        });
        assertEquals(EMAIL_JA_CADASTRO_NO_SISTEMA, exception.getMessage());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        when(repository.save(any())).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO response = service.update(userDTO);

        assertEquals(UserDTO.class, response.getClass());
        assertNotNull(response);
        assertEquals(ID, response.getKey());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertNotSame(PASSWORD, response.getPassword());
    }

    @Test
//    whenUpdateThenReturnDataIntegrityViolationException
    void shouldReturnDataIntegrityViolationExceptionWhenUpdatingUserWithDuplicateEmail() {
        when(repository.findByEmail(anyString())).thenReturn(userOptional);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userOptional.get().setKey(2L);
            service.update(userDTO);
        });

        assertEquals(EMAIL_JA_CADASTRO_NO_SISTEMA, exception.getMessage());
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        when(repository.findById(anyLong())).thenReturn(userOptional);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
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
        Address address = new Address(STREET, NUMBER, COMPLEMENT, CITY, STATE, COUNTRY);
        user = new Users(ID, NAME, EMAIL, address, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, address, PASSWORD);
        userOptional = Optional.of(new Users(ID, NAME, EMAIL, address, PASSWORD));
    }
}