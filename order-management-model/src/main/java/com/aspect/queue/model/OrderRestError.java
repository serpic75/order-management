package com.aspect.queue.model;

public class OrderRestError {
    private final String uri;
    private final int statusCode;
    private final String errorText;

    public OrderRestError(int statusCode, String errorText, String uri){
        this.statusCode = statusCode;
        this.errorText=errorText;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorText() {
        return errorText;
    }
}
