package br.com.waltim.api.resources;

import br.com.waltim.api.domain.Users;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
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
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyLong())).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO, response.getBody());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        user = new Users(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}