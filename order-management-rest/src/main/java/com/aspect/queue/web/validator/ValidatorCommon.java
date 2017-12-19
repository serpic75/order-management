package com.aspect.queue.web.validator;

import com.aspect.queue.model.exceptions.OrderInvalidValueException;
import com.google.common.base.Strings;

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
        if (Strings.isNullOrEmpty(obj)) {
            throw new OrderInvalidValueException(valueType, valueInstance);
        }
    }
}
