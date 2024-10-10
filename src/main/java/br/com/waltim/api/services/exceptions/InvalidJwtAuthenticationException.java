package br.com.waltim.api.services.exceptions;

import org.springframework.security.core.AuthenticationException;

import java.io.Serializable;

public class InvalidJwtAuthenticationException extends AuthenticationException implements Serializable {
    private static final long serialVersionUID = 1L;

    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }
}
