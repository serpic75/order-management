package com.aspect.queue.model.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicatedOrderException extends OrderException {

    private final static HttpStatus ERROR_STATUS = HttpStatus.CONFLICT;


    public DuplicatedOrderException(String message){
        super(message);
        super.statusCode=ERROR_STATUS.value();
    }

    public HttpStatus getStatus() {
        return ERROR_STATUS;
    }
}
