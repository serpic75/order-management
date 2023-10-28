package com.aspect.queue.web.validator;


import com.aspect.queue.model.transformers.OrderRepresentation;
import org.springframework.stereotype.Component;

import static com.aspect.queue.model.transformers.OrderRepresentation.ORDER_ID;

@Component
public class OrderValidator implements Validator<OrderRepresentation>{

    public void validate(OrderRepresentation input) {
        ValidatorCommon.checkNotNull(input, "Order object can't be null");
        ValidatorCommon.checkNotNullAndNotEmpty(String.valueOf(input.getRepresentationId()), "mandatory attribute", ORDER_ID);
    }
}
