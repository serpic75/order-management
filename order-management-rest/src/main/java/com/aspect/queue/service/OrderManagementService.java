package com.aspect.queue.service;

import com.aspect.queue.converters.ClassIdentifierConverter;
import com.aspect.queue.converters.Converter;
import com.aspect.queue.entity.ClassifiedIdentifierEntity;
import com.aspect.queue.entity.OrderEntity;
import com.aspect.queue.model.ClassifiedIdentifier;
import com.aspect.queue.model.Order;
import com.aspect.queue.model.exceptions.OrderInvalidValueException;
import com.aspect.queue.model.provider.BaseProvider;
import com.aspect.queue.repo.ClassifiedIdentifierRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.aspect.queue.repo.OrderRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier(value= "orderManagementService")
public class OrderManagementService implements BaseProvider<Order> {
	private static ModelMapper mapper = new ModelMapper();
	private final OrderRepo repo;
	private final ClassifiedIdentifierRepo classifiedIdentifierRepo;
	private final Converter<ClassifiedIdentifier, ClassifiedIdentifierEntity> converter;
	private final Converter<Order, OrderEntity> orderConverter;

	public OrderManagementService(OrderRepo repo, ClassifiedIdentifierRepo classifiedIdentifierRepo,
			ClassIdentifierConverter converter, Converter<Order, OrderEntity> orderConverter){
		this.repo = repo;
		this.classifiedIdentifierRepo = classifiedIdentifierRepo;
		this.converter = converter;
		this.orderConverter = orderConverter;
	}

	public Order findById(Long id){
		return mapper.map(repo.findById(id).orElseThrow(() -> new OrderInvalidValueException("Resource doesn't exist")),
				Order.class);
	}

	@Override public Order find(Order key) {
		return null;
	}

	@Override public List<Order> findAll() {
		return repo.findAll().stream().map(a -> orderConverter.convertFromEntity(a)).collect(Collectors.toList());
	}

	@Override
	public boolean delete(Long id) {
		return true;
	}

	@Override
	public Order create(Order order) {
		ClassifiedIdentifierEntity classifiedIdentifierEntity =
				classifiedIdentifierRepo.save(converter.convertFromBean(order.getId()));
		OrderEntity orderEntity = mapper.map(order, OrderEntity.class);
		orderEntity.setId(classifiedIdentifierEntity.getId());
		OrderEntity orderSaved = repo.save(orderEntity);
		return orderConverter.convertFromEntity(orderSaved);
	}

	@Override public Optional<Order> findTop() {
		Optional<OrderEntity> top = repo.findBy();
		if(top.isPresent()){
			return Optional.of(mapper.map(top.get(), Order.class));
		}
		return Optional.empty();
	}

	@Override public Integer findPosition(Order order) {
		return null;
	}

	@Override public Double averageWaitingTime() {
		return null;
	}

	@Override public Boolean isPresent(Order order) {
		return null;
	}
}
