package com.aspect.queue.repo;

import com.aspect.queue.entity.ClassifiedIdentifierEntity;
import com.aspect.queue.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClassifiedIdentifierRepo extends JpaRepository<ClassifiedIdentifierEntity, Long> {
	@Query(nativeQuery = true, name = "SELECT * FROM classified_identifier_id with(nolock) where id in (SELECT max(id) FROM classified_identifier_id with(nolock)) ")
	Optional<ClassifiedIdentifierEntity> findBy();

}
