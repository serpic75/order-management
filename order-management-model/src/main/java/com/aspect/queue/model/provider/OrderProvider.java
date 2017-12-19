package com.aspect.queue.model.provider;

import com.aspect.queue.model.ClassifiedIdentifier;
import com.aspect.queue.model.Order;
import com.aspect.queue.model.UniquePriorityBlockingQueue;
import com.aspect.queue.model.exceptions.OrderException;
import com.aspect.queue.model.exceptions.OrderInvalidValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderProvider implements BaseProvider<Order> {

    private static Logger logger = LoggerFactory.getLogger(OrderProvider.class);

    @Autowired
    private UniquePriorityBlockingQueue uniquePriorityBlockingQueue;

    @Override
    public Optional<Order> findTop() {
        if (!uniquePriorityBlockingQueue.isEmpty()) {
            try {
                return Optional.of(uniquePriorityBlockingQueue.take());
            } catch (InterruptedException e) {
                logger.warn("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
        }
        throw new OrderException("Empty queue");
    }

    @Override
    public Integer findPosition(Order order) {
        if (uniquePriorityBlockingQueue.isEmpty()) {
            return -1;
        }

        return uniquePriorityBlockingQueue.getIndexOf(order);
    }

    @Override
    public Double averageWaitingTime() {
        return uniquePriorityBlockingQueue.stream().mapToDouble(Order::getWaitingTime).average().getAsDouble();
    }

    @Override
    public Boolean isPresent(Order order) {
        return uniquePriorityBlockingQueue.contains(order);
    }

    @Override
    public Order find(Order key) {
        if (uniquePriorityBlockingQueue.remove(key)) {
            return key;
        }
        throw new OrderInvalidValueException("Order not found");
    }

    @Override
    public List<Order> findAll() {
        return uniquePriorityBlockingQueue.stream().collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        if (uniquePriorityBlockingQueue.isEmpty()) {
            Order order = new Order(new ClassifiedIdentifier(id));

            if (uniquePriorityBlockingQueue.contains(order)) {
                return uniquePriorityBlockingQueue.remove(new Order(new ClassifiedIdentifier(id)));
            } else {
                throw new OrderException("No item found");
            }
        }
        throw new OrderException("Empty Queue");
    }

    @Override
    public Order create(Order order) {
        uniquePriorityBlockingQueue.insert(order);
        return order;
    }
}
