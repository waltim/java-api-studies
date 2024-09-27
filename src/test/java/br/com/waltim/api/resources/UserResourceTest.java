package br.com.waltim.api.resources;

import br.com.waltim.api.domain.Users;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.domain.vo.Address;
import br.com.waltim.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class UserResourceTest {

    @InjectMocks
    private UserResource resource;
    @Mock
    private ModelMapper mapper;
    @Mock
    private UserServiceImpl service;

    private Users user;
    private UserDTO userDTO;

    public static final long ID = 1L;
    public static final String NAME = "UserTest";
    public static final String EMAIL = "teste@teste.com";
    public static final String PASSWORD = "123321";

    @BeforeEach
    void setUp() {
        openMocks(this);
        startUser();
    }

    @Test
    void shouldReturnSuccessWhenFindByIdT() {
        when(service.findById(anyLong())).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO, response.getBody());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        // uso de JsonProperty.Access.WRITE_ONLY
//        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void shouldReturnSuccessWhenfindAll() {
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(userDTO), response.getBody());

        assertEquals(ID, response.getBody().getFirst().getId());
        assertEquals(NAME, response.getBody().getFirst().getName());
        assertEquals(EMAIL, response.getBody().getFirst().getEmail());
        // uso de JsonProperty.Access.WRITE_ONLY
//        assertEquals(PASSWORD, response.getBody().getFirst().getPassword());
    }

    @Test
    void shouldReturnSuccessWhenCreatingUser() {
        when(service.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.create(userDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
    }

    @Test
    void shouldReturnAnUserWhenupdatingUser() {
        when(service.update(any())).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        // uso de JsonProperty.Access.WRITE_ONLY
//        assertEquals(PASSWORD, response.getBody().getPassword());
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

    private void startUser() {
        Address address = new Address("Main Street", "123", "Apt 4B", "Springfield", "IL", "USA");
        user = new Users(ID, NAME, EMAIL, address, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, address, PASSWORD);
    }
}