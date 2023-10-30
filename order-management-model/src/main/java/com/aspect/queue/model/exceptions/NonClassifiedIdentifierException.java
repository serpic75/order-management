package com.aspect.queue.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NonClassifiedIdentifierException extends OrderException {

    public NonClassifiedIdentifierException(String message) {
        super(message);
    }
}
