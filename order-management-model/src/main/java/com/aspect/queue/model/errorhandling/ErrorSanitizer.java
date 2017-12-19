package com.aspect.queue.model.errorhandling;

import com.aspect.queue.model.OrderRestError;
import com.aspect.queue.model.exceptions.OrderException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.UnmarshalException;

/**
 * Utility class providing methods to sanitize error messages
 */
public class ErrorSanitizer {
    private static final String DEFAULT_ERROR_MESSAGE = "Invalid data";

    /**
     * public constructor to hide the implicit one
     */
    private ErrorSanitizer(){

    }

    /**
     * Head requests must not have a body, otherwise they cause issues on the client side,
     * return only full error information in OrderRestError when no HEAD request was performed
     *
     * @param restError - The original OrderRestError
     * @param request   - The original request
     * @return - Returns null for "HEAD" requests, otherwise returns the provided OrderRestError
     */
    public static OrderRestError sanitizeRestError(OrderRestError restError, HttpServletRequest request) {
        OrderRestError sanitizedError = restError;

        if ("HEAD".equals(request.getMethod())) {
            sanitizedError = null;
        }

        return sanitizedError;
    }

    /**
     * Extracts a sanitized error message from a given Throwable, that can be used in error logs / reporting
     *
     * @param t - the Throwable to get the sanitized error message
     * @return - Returns the sanitized error message
     */
    public static String getSanitizedErrorMessage(Throwable t) {
        String returnValue;

        if (t == null) {
            return DEFAULT_ERROR_MESSAGE;
        }

        //JsonProcessingException leak information about the parser and the stream -->
        //sanitize the output by using getOriginalMessage
        if (t instanceof InvalidFormatException) {
            returnValue = ((InvalidFormatException) t).getOriginalMessage();
        } else if (t instanceof JsonMappingException) {
            JsonMappingException o_O = (JsonMappingException)t;
            JsonParseException jpe = (JsonParseException)o_O.getCause();
            returnValue = jpe != null ? jpe.getOriginalMessage() : o_O.getOriginalMessage();
        } else if (t instanceof JsonProcessingException) {
            JsonProcessingException e = (JsonProcessingException) t;
            returnValue = e.getOriginalMessage();
        } else if (t instanceof UnmarshalException) {
            //Xml UnmarshalException don't contain any information --> use the underlying exception for error reporting
            returnValue = t.getCause() != null ? t.getCause().getLocalizedMessage() : t.getLocalizedMessage();
        } else if (t instanceof OrderException) {
            // Order Exceptions use error text for some reason
            returnValue = ((OrderException) t).getErrorText();
        } else if (!Strings.isNullOrEmpty(t.getLocalizedMessage())) {
            returnValue = t.getLocalizedMessage();
        } else {
            returnValue = t.getMessage();
        }

        return returnValue != null ? returnValue : DEFAULT_ERROR_MESSAGE;
    }
}
