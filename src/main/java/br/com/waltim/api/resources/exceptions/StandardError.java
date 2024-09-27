package br.com.waltim.api.resources.exceptions;

import java.time.LocalDateTime;


public class StandardError {
    private final LocalDateTime timestamp;
    private final Integer status;
    private final String error;
    private final String path;

    public StandardError(LocalDateTime timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
