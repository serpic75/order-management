package com.aspect.queue.model.transformers;

import com.aspect.queue.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderTransformer implements Transformer<Order, OrderRepresentation> {

    @Override
    public OrderRepresentation transform(Order entity) {

        return new OrderRepresentationBuilder(entity.getId().getId()).withTimestamp(entity.getTimestamp())
                .withPriority(entity.getPriority()).withSeconds(entity.getWaitingTime()).createRepresentation();
    }

    @Override
    public OrderRepresentation transformValue(Number number) {
        if(number instanceof Double){
            return new OrderRepresentationBuilder().withAverageWaitTime((Double) number).createRepresentation();
        }
        return new OrderRepresentationBuilder().withOrderPosition((Integer) number).createRepresentation();
    }

    @Override
    public Order extract(OrderRepresentation representation) {

        return new OrderRepresentationBuilder(representation.getRepresentationId()).withTimestamp(representation.getOrderInsertDate()).create();
    }
}

