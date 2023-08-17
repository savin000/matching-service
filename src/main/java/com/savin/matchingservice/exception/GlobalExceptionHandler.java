package com.savin.matchingservice.exception;

import com.savin.matchingservice.constants.GlobalExceptionHandlerMessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        log.error(GlobalExceptionHandlerMessageConstants.BASE_MESSAGE, e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(GlobalExceptionHandlerMessageConstants.BASE_MESSAGE + e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(GlobalExceptionHandlerMessageConstants.ENTITY_NOT_FOUND_MESSAGE, e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(GlobalExceptionHandlerMessageConstants.ENTITY_NOT_FOUND_MESSAGE + e.getMessage()));
    }
}
