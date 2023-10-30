package com.aspect.queue.service;

import com.aspect.queue.entity.OrderEntity;
import com.aspect.queue.model.Order;
import com.aspect.queue.model.exceptions.OrderInvalidValueException;
import com.aspect.queue.model.provider.BaseProvider;
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

	public OrderManagementService(OrderRepo repo){
		this.repo = repo;
	}

	public Order findById(Long id){
		return mapper.map(repo.findById(id).orElseThrow(() -> new OrderInvalidValueException("Resource doesn't exist")),
				Order.class);
	}

	@Override public Order find(Order key) {
		return null;
	}

	@Override public List<Order> findAll() {
		return repo.findAll().stream().map(a -> mapper.map(a, Order.class)).collect(Collectors.toList());
	}

	@Override
	public boolean delete(Long id) {
		return true;
	}

	@Override
	public Order create(Order order) {
		OrderEntity orderSaved = repo.save(mapper.map(order, OrderEntity.class));
		return mapper.map(orderSaved, Order.class);
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
