package com.aspect.queue.builders;

import com.aspect.queue.entity.ClassIdEntity;
import com.aspect.queue.entity.ClassifiedIdentifierEntity;

public class ClassifiedIdentifierEntityBuilder {

	private Long id;
	private ClassIdEntity idClass;


	public ClassifiedIdentifierEntityBuilder(){

	}
	public ClassifiedIdentifierEntityBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public ClassifiedIdentifierEntityBuilder withIdClass(ClassIdEntity classId) {
		this.idClass = classId;
		return this;
	}

	public ClassifiedIdentifierEntity build(){
		ClassifiedIdentifierEntity classifiedIdentifierEntity = new ClassifiedIdentifierEntity();
		classifiedIdentifierEntity.setIdClass(this.idClass);
		classifiedIdentifierEntity.setId(this.id);
		return classifiedIdentifierEntity;
	}
}
