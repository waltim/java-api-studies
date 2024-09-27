package br.com.waltim.api.services.exceptions;

public class HandleIllegalArgumentException extends RuntimeException {
    public HandleIllegalArgumentException(String message) {
        super(message);
    }
}
