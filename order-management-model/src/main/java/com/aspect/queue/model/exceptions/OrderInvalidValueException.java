package com.aspect.queue.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class OrderInvalidValueException extends OrderException {

    protected String valueType;
    protected String valueInstance;
    protected static String valueProblem= "Invalid";

    private static final long serialVersionUID = -8169049754693193514L;

    public OrderInvalidValueException(String valueType) {
        super(valueType, null);
    }

    public OrderInvalidValueException(String valueType, String valueInstance) {
        this.valueType = valueType;
        this.valueInstance = valueInstance;
    }

}
