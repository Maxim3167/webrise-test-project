package com.maxim.webrise.handler;

import com.maxim.webrise.exception.SubscribeAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HttpStatus> entityNotFoundException(EntityNotFoundException exception) {
        log.error("Entity not found. " + exception.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SubscribeAlreadyExistsException.class)
    public ResponseEntity<HttpStatus> badRequestSubscribe(SubscribeAlreadyExistsException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
