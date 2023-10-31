package com.aspect.queue.converters;

import com.aspect.queue.builders.OrderEntityBuilder;
import com.aspect.queue.entity.ClassifiedIdentifierEntity;
import com.aspect.queue.entity.OrderEntity;
import com.aspect.queue.model.Order;
import com.aspect.queue.model.builders.OrderBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderConverter extends Converter<Order, OrderEntity>{

	public OrderConverter() {
		super(OrderConverter::convertToEntity, OrderConverter::convertToBean);
	}

	private static OrderEntity convertToEntity(Order bean) {
		return new OrderEntityBuilder()
				.withTimestamp(bean.getTimestamp())
				.withPriority(bean.getPriority())
				.withWaitingTime(bean.getWaitingTime())
				.build();
	}

	private static Order convertToBean(OrderEntity orderEntity) {
		return new OrderBuilder()
				.withId(orderEntity.getId())
				.withTimestamp(orderEntity.getTimestamp())
				.withPriority(orderEntity.getPriority())
				.withWaitingTime(orderEntity.getWaitingTime())
				.build();
	}
}
