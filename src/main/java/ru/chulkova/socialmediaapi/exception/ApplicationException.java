package ru.chulkova.socialmediaapi.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private String message;

    public ApplicationException(String message) {
        this.message = message;
    }
}
