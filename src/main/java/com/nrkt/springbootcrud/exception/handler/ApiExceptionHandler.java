package com.nrkt.springbootcrud.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.RollbackException;
import javax.validation.UnexpectedTypeException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.error(ex.getMessage());

        HashMap<String, Map<String, String>> errorList = new HashMap<>();
        errorList.put("errors:", errors);
        return errorList;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RollbackException.class)
    public Map<String, String[]> handleRollBackExceptions(RollbackException ex) {
        log.error(ex.getMessage() + "\n" + ex.getCause().getMessage());

        String[] error = {"Process; an error occurred while saving to data, " +
                "the operation could not be completed."};
        HashMap<String, String[]> errorList = new HashMap<>();
        errorList.put("errors:", error);
        return errorList;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnexpectedTypeException.class)
    public Map<String, String[]> handleUnexpectedTypeExceptions(UnexpectedTypeException ex) {
        log.error(ex.getMessage() + "\n" + ex.getCause().getMessage());

        String[] error = {"Process; an error occurred, the operation could not be completed."};
        HashMap<String, String[]> errorList = new HashMap<>();
        errorList.put("errors:", error);
        return errorList;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public Map<String, String[]> emptyResultDataAccessExceptionExceptions(EmptyResultDataAccessException ex) {
        log.error(ex.getMessage() + "\n" + ex.getCause().getMessage());

        String[] error = {"Process; no record exists according to the specified value"};
        HashMap<String, String[]> errorList = new HashMap<>();
        errorList.put("errors:", error);
        return errorList;
    }
}
