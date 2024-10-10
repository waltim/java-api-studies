package br.com.waltim.api.resources.exceptions;

import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.HandleIllegalArgumentException;
import br.com.waltim.api.services.exceptions.InvalidJwtAuthenticationException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError>objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError>dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HandleIllegalArgumentException.class)
    public ResponseEntity<StandardError> handleIllegalArgumentException(HandleIllegalArgumentException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<StandardError> handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}
