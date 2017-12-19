package com.aspect.queue.model.transformers;

import com.aspect.queue.model.ClassifiedIdentifier;
import com.aspect.queue.model.Order;

public class OrderRepresentationBuilder {

    private Long id;
    private Long priority;
    private Long timestamp;
    private Long seconds;
    private Integer orderPosition;
    private Double averageWaitTime;

    public OrderRepresentationBuilder(){

    }

    public OrderRepresentationBuilder(Long id) {
        this.id = id;
    }

    public OrderRepresentationBuilder withTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
    
    public OrderRepresentationBuilder withPriority(Long priority) {
        this.priority = priority;
        return this;
    }

    public OrderRepresentationBuilder withSeconds(Long seconds) {
        this.seconds = seconds;
        return this;
    }

    public OrderRepresentationBuilder withOrderPosition(Integer orderPosition) {
        this.orderPosition = orderPosition;
        return this;
    }

    public OrderRepresentationBuilder withAverageWaitTime(Double averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
        return this;
    }

    public OrderRepresentation createRepresentation() {
        OrderRepresentation representation = new OrderRepresentation(id, timestamp, priority, seconds, orderPosition, averageWaitTime);

        return representation;
    }

    public Order create() {
        Order order = new Order(new ClassifiedIdentifier(id), timestamp);

        return order;
    }

}

