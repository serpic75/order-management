package com.aspect.queue.web.validator;

import com.aspect.queue.model.exceptions.OrderInvalidValueException;

import java.util.Objects;

/**
 * Simple Utility Class providing static methods for input checking and throwing proper {@code OrderInvalidValueException}
 */
public class ValidatorCommon {

    public static void checkNotNull(Object obj, String description) {
        checkNotNull(obj, description, null);
    }

    public static void checkNotNull(Object obj, String valueType, String valueInstance) throws
            OrderInvalidValueException {
        if (obj == null) {
            throw new OrderInvalidValueException(valueType, valueInstance);
        }
    }

    public static void checkNotNullAndNotEmpty(String obj, String valueType,
            String valueInstance) {
        if (!Objects.requireNonNull(obj).isEmpty()) {
            throw new OrderInvalidValueException(valueType, valueInstance);
        }
    }
}
