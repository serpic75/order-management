package com.aspect.queue.model.exceptions;

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
