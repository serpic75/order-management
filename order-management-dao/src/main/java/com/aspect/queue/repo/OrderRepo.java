package com.aspect.queue.repo;

import com.aspect.queue.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
	@Query(nativeQuery = true, name = "SELECT * FROM aspect_order with(nolock) where id in (SELECT max(id) FROM aspect_order with(nolock)) ")
	Optional<OrderEntity> findBy();
}
