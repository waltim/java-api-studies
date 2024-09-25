package br.com.waltim.api.resources.exceptions;

import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void shouldReturnResponseEntityWhenObjectNotFound() {
        ResponseEntity<StandardError> responseEntity = exceptionHandler.objectNotFound(
                new ObjectNotFoundException("Usuário não encontrado"),
                new MockHttpServletRequest()
        );

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(ResponseEntity.class, responseEntity.getClass());
        assertEquals(StandardError.class, responseEntity.getBody().getClass());
        assertEquals("Usuário não encontrado",responseEntity.getBody().getError());
        assertEquals(404,responseEntity.getStatusCodeValue());
    }

    @Test
    void shouldReturnResponseEntityWhenDataIntegrityViolationException() {
        ResponseEntity<StandardError> responseEntity = exceptionHandler.dataIntegrityViolationException(
                new DataIntegrityViolationException("Email já cadastro no sistema."),
                new MockHttpServletRequest()
        );

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ResponseEntity.class, responseEntity.getClass());
        assertEquals(StandardError.class, responseEntity.getBody().getClass());
        assertEquals("Email já cadastro no sistema.",responseEntity.getBody().getError());
        assertEquals(400,responseEntity.getStatusCodeValue());
    }
}