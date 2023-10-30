package com.aspect.queue.entity;

import javax.persistence.*;

@Entity
@Table(name = "aspect_order")
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "timestamp", nullable = false)
	private Long timestamp;

	@Column(name = "priority", nullable = false)
	private Long priority;

	@Column(name = "waiting_time", nullable = false)
	private long waitingTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
