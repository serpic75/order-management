package com.aspect.queue.model.exceptions;

import org.apache.commons.lang.exception.ExceptionUtils;

public class OrderException extends RuntimeException {

    private String causeText;
    protected String errorText = "Internal Error";
    protected int statusCode = 500;

    public OrderException() {
        super();
    }

    public OrderException(String message) {
        super(message);
    }

    public OrderException(String message, Throwable cause) {
        this(message, cause, false, false);
    }

    public OrderException(Throwable cause) {
        this(null, cause, false, false);
    }

    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause == null ? null : new Exception(ExceptionUtils.getFullStackTrace(cause)),
                enableSuppression, writableStackTrace);
        if (cause != null) {
            causeText = "Cause message:" + cause.getMessage() + "\n Cause: " + cause.toString();
        }
    }

    public String getErrorText() {
        return errorText;
    }

    public String getCauseText() {
        return causeText;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
