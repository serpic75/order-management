package com.aspect.queue.entity;

import javax.persistence.*;


@Entity
@Table(name = "classified_identifier")
public class ClassifiedIdentifierEntity {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_class")
	private ClassIdEntity idClass;

	public ClassIdEntity getIdClass() {
		return idClass;
	}

	public void setIdClass(ClassIdEntity idClass) {
		this.idClass = idClass;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
