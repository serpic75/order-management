package com.aspect.queue.entity;

import javax.persistence.*;

@Entity
@Table(name = "aspect_order")
public class OrderEntity {
	@Id
	@Column(name = "classified_identifier_id")
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

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public long getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}
}
