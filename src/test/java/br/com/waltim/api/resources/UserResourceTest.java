package br.com.waltim.api.resources;

import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.domain.vo.Address;
import br.com.waltim.api.services.UserService;

import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserResourceTest {

    @InjectMocks
    private UserResource resource;
    @Mock
    private UserService service;

    private UserDTO userDTO;

    public static final long ID = 1L;
    public static final String NAME = "UserTest";
    public static final String EMAIL = "teste@teste.com";
    public static final String PASSWORD = "123321";

    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    public static final String EMAIL_JA_CADASTRO_NO_SISTEMA = "Email já cadastro no sistema.";

    @BeforeEach
    void setUp() {
        startUser();
    }

    @Test
    void shouldReturnSuccessWhenFindById() {
        when(service.findById(anyLong())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());

        assertEquals(ID, Objects.requireNonNull(response.getBody()).getKey());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        // uso de JsonProperty.Access.WRITE_ONLY
//        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void shouldReturnObjectNotFoundExceptionWhenFindById() {
        when(service.findById(anyLong())).thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> resource.findById(ID));

        assertNotNull(exception);
        assertEquals(USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenFindAll() {
        when(service.findAll()).thenReturn(List.of(userDTO));

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(userDTO), response.getBody());

        assertEquals(ID, Objects.requireNonNull(response.getBody()).getFirst().getKey());
        assertEquals(NAME, response.getBody().getFirst().getName());
        assertEquals(EMAIL, response.getBody().getFirst().getEmail());
    }

    @Test
    void shouldReturnObjectNotFoundExceptionWhenCreatingUser() {
        when(service.create(any())).thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> resource.create(userDTO));
        assertNotNull(exception);
        assertEquals(USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    void shouldReturnAnUserWhenUpdatingUser() {
        when(service.create(any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.create(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody());

    }

    @Test
    void shouldReturnObjectNotFoundExceptionWhenUpdatingUser() {
        when(service.update(any())).thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));

        ObjectNotFoundException exception =  assertThrows(ObjectNotFoundException.class, () -> resource.update(ID,userDTO));
        assertNotNull(exception);
        assertEquals(USUARIO_NAO_ENCONTRADO, exception.getMessage());

    }

    @Test
    void shouldReturnDataIntegrityViolationExceptionWhenUpdatingUser(){
        when(service.update(any())).thenThrow(new DataIntegrityViolationException(EMAIL_JA_CADASTRO_NO_SISTEMA));

        DataIntegrityViolationException exception =  assertThrows(DataIntegrityViolationException.class, () -> resource.update(ID,userDTO));
        assertNotNull(exception);
        assertEquals(EMAIL_JA_CADASTRO_NO_SISTEMA, exception.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenDeletingUser() {
        doNothing().when(service).delete(anyLong());

        ResponseEntity<UserDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());

        verify(service, times(1)).delete(anyLong());
    }

    @Test
    void shouldReturnObjectNotFoundExceptionWhenDeletingUser(){
        doThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO)).when(service).delete(anyLong());
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> resource.delete(ID));
        assertNotNull(exception);
        assertEquals(USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }

    private void startUser() {
        Address address = new Address("Main Street", "123", "Apt 4B", "Springfield", "IL", "USA");
        userDTO = new UserDTO(ID, NAME, EMAIL, address, PASSWORD);
        userDTO.add(Collections.emptyList());
    }
}