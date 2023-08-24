package ru.chulkova.socialmediaapi.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class AccessDeniedException extends ApplicationException{

    public AccessDeniedException() {
        super(HttpStatus.FORBIDDEN, "Access denied", ErrorAttributeOptions.of(MESSAGE));
    }
}