package br.com.waltim.api.resources.exceptions;

import br.com.waltim.api.domain.vo.Address;
import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.HandleIllegalArgumentException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ResourceExceptionHandlerTest {

    public static final String CANNOT_BE_NULL_OR_EMPTY = "Street, city, state, and country cannot be null or empty";
    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    private StandardError standardError;

    @BeforeEach
    void setUp() {
        startStandardError();
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

        assertEquals(404,responseEntity.getStatusCode().value());
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
        assertEquals(400,responseEntity.getStatusCode().value());
    }


    @Test
    void shouldReturnResponseEntityWhenHandleIllegalArgumentException(){
        ResponseEntity<StandardError> responseEntity = exceptionHandler.handleIllegalArgumentException(
                new HandleIllegalArgumentException(CANNOT_BE_NULL_OR_EMPTY),
                new MockHttpServletRequest()
        );

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ResponseEntity.class, responseEntity.getClass());
        assertEquals(standardError.getClass(), responseEntity.getBody().getClass());

        assertEquals(responseEntity.getBody().getError(),standardError.getError());
        assertEquals(responseEntity.getBody().getStatus(),standardError.getStatus());
        assertNotNull(responseEntity.getBody().getTimestamp());
        assertNotNull(responseEntity.getBody().getPath());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenStreetIsEmpty() {
        HandleIllegalArgumentException exception = assertThrows(HandleIllegalArgumentException.class, () -> new Address("", "123", "Apt 1", "City", "State", "Country"));
        assertNotNull(exception);
        assertEquals(CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenStreetIsNull() {
        HandleIllegalArgumentException exception = assertThrows(HandleIllegalArgumentException.class, () -> new Address(null, "123", "Apt 1", "City", "State", "Country"));

        assertNotNull(exception);
        assertEquals(CANNOT_BE_NULL_OR_EMPTY,exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNumberIsEmpty() {
        HandleIllegalArgumentException exception = assertThrows(HandleIllegalArgumentException.class, () -> new Address("Street 1", "", "Apt 1", "City", "State", "Country"));
        assertNotNull(exception);
        assertEquals(CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNumberIsNull() {
        HandleIllegalArgumentException exception = assertThrows(HandleIllegalArgumentException.class, () -> new Address("Street 1", null, "Apt 1", "City", "State", "Country"));
        assertNotNull(exception);
        assertEquals(CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCityIsEmpty() {
        HandleIllegalArgumentException exception = assertThrows(HandleIllegalArgumentException.class, () -> new Address("Street 1", "123", "Apt 1", "", "State", "Country"));
        assertNotNull(exception);
        assertEquals(CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCityIsNull() {
        HandleIllegalArgumentException exception = assertThrows(HandleIllegalArgumentException.class, () -> new Address("Street 1", "123", "Apt 1", null, "State", "Country"));
        assertNotNull(exception);
        assertEquals(CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenStateIsEmpty() {
        HandleIllegalArgumentException exception = assertThrows(HandleIllegalArgumentException.class, () -> new Address("Street 1", "123", "Apt 1", "Bsb", "", "Country"));
        assertNotNull(exception);
        assertEquals(CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenStateIsNull() {
        HandleIllegalArgumentException exception = assertThrows(HandleIllegalArgumentException.class, () -> new Address("Street 1", "123", "Apt 1", "Bsb", null, "Country"));
        assertNotNull(exception);
        assertEquals(CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    private void startStandardError(){
        LocalDateTime errorDateTime = LocalDateTime.of(2023, 1, 15, 10, 0);
        standardError = new StandardError(errorDateTime,HttpStatus.BAD_REQUEST.value(),CANNOT_BE_NULL_OR_EMPTY,"/v1/book/");
    }
}