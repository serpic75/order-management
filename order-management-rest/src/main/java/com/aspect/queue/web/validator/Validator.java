package com.aspect.queue.web.validator;

import com.aspect.queue.model.exceptions.OrderException;

public interface Validator<T> {
    /**
     * Throws exception if the input is invalid.
     */
    void validate(T input) throws OrderException;
}
