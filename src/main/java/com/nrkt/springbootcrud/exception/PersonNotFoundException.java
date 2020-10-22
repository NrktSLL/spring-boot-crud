package com.nrkt.springbootcrud.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Person not found!")
public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
        log.error("Person not found!");
    }
}
