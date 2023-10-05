package ru.chulkova.socialmediaapi.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.chulkova.socialmediaapi.exception.AccessDeniedException;
import ru.chulkova.socialmediaapi.exception.ApplicationException;
import ru.chulkova.socialmediaapi.exception.NotFoundException;
import ru.chulkova.socialmediaapi.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> appException(WebRequest request, ApplicationException ex) {
        log.error("ApplicationException: {}", ex.getMessage());
        return createResponseEntity(request, ErrorAttributeOptions.of(MESSAGE),
                ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<?> entityNotFoundException(WebRequest request, NotFoundException ex) {
        log.error("NotFoundException: {}", ex.getMessage());
        return createResponseEntity(request, ex.getOptions(),
                ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseStatusException validationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException: {}", ex.getMessage());
        List<String> message = new ArrayList<>();
        ex.getConstraintViolations().forEach(constraintViolation -> message.add(constraintViolation.getMessage()));
        return new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException e, WebRequest request) {
        log.error("AccessDeniedException: {}", e.getMessage());
        return createResponseEntity(request, ErrorAttributeOptions.of(MESSAGE),
                e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> createResponseEntity(WebRequest request,
                                                       ErrorAttributeOptions options,
                                                       String msg, HttpStatus status) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, options);
        if (msg != null) {
            body.put("message", msg);
        }
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        return (ResponseEntity<T>) ResponseEntity.status(status).body(body);
    }
}