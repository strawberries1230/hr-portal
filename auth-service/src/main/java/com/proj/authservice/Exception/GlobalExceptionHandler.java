package com.proj.authservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(AlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFoundException(EmailNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = FailtoRegisterException.class)
    public ResponseEntity<?> handleFailtoRegisterException(FailtoRegisterException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
