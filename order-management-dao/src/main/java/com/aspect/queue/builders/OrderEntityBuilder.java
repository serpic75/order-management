package com.aspect.queue.builders;

import com.aspect.queue.entity.OrderEntity;

public class OrderEntityBuilder {

	private Long id;
	private Long timestamp;
	private Long priority;
	private long waitingTime;

	public OrderEntityBuilder withId( Long id) {
		this.id = id;
		return this;
	}

	public OrderEntityBuilder withTimestamp(Long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public OrderEntityBuilder withPriority(Long priority) {
		 this.priority = priority;
		return this;
	}

	public OrderEntityBuilder withWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
		return this;
	}

	public OrderEntity build(){
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(this.id);
		orderEntity.setPriority(this.priority);
		orderEntity.setTimestamp(this.timestamp);
		orderEntity.setWaitingTime(this.waitingTime);
		return orderEntity;
	}
}
