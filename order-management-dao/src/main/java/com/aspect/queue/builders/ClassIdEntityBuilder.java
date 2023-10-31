package com.aspect.queue.builders;

import com.aspect.queue.entity.ClassIdEntity;

public class ClassIdEntityBuilder {

	private String classId;
	private Integer id;

	public ClassIdEntityBuilder(){

	}
	public ClassIdEntityBuilder withId(Integer id) {
		this.id = id;
		return this;
	}

	public ClassIdEntityBuilder withClassId(String classId) {
		this.classId = classId;
		return this;
	}

	public ClassIdEntity build() {
		ClassIdEntity classIdEntity = new ClassIdEntity();
		classIdEntity.setId(this.id);
		classIdEntity.setClassId(this.classId);
		return classIdEntity;
	}
}
