package com.aspect.queue.model.builders;

import com.aspect.queue.model.ClassifiedIdentifier;
import com.aspect.queue.model.Order;

public class OrderBuilder {

	private ClassifiedIdentifier id;
	private Long timestamp;
	private Long priority;
	private long waitingTime;

	public OrderBuilder withId( Long id) {
		this.id = new ClassifiedIdentifier(id);
		return this;
	}

	public OrderBuilder withTimestamp(Long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public OrderBuilder withPriority(Long priority) {
		this.priority = priority;
		return this;
	}

	public OrderBuilder withWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
		return this;
	}

	public Order build(){
		Order order = new Order(this.id);
		order.setPriority(this.priority);
		order.setTimestamp(this.timestamp);
		order.setWaitingTime(this.waitingTime);
		return order;
	}
}
