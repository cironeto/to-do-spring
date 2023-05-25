package dev.cironeto.todospring.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> emailAlreadyExists(BadRequestException e, HttpServletRequest request) {
        var error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setMessage("Conflict while trying to reach the resource");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Database conflict");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        var error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setMessage("The resource requested was not found in the database");
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Resource not found");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrityViolation(DataIntegrityException e, HttpServletRequest request) {
        var error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setMessage("Cannot execute the request due to a data integrity violation");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Data integrity violation");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotAllowedException.class)
    public ResponseEntity<StandardError> userNotAllowed(UserNotAllowedException e, HttpServletRequest request) {
        var error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setMessage("The logged in user cannot execute this request");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("User not allowed");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
