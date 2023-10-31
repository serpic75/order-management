package com.aspect.queue.converters;

import com.aspect.queue.builders.ClassIdEntityBuilder;
import com.aspect.queue.builders.ClassifiedIdentifierEntityBuilder;
import com.aspect.queue.entity.ClassifiedIdentifierEntity;
import com.aspect.queue.model.ClassifiedIdentifier;
import org.springframework.stereotype.Service;

@Service
public class ClassIdentifierConverter extends Converter<ClassifiedIdentifier, ClassifiedIdentifierEntity>{

	public ClassIdentifierConverter() {
		super(ClassIdentifierConverter::convertToEntity, ClassIdentifierConverter::convertToBean);
	}

	private static ClassifiedIdentifierEntity convertToEntity(ClassifiedIdentifier bean) {
		return new ClassifiedIdentifierEntityBuilder().withIdClass(
				new ClassIdEntityBuilder()
						.withClassId(String.valueOf(bean.getIdClass()))
						.withId((bean.getRank()))
						.build())
				.withId(bean.getId())
				.build();
	}

	private static ClassifiedIdentifier convertToBean(ClassifiedIdentifierEntity classifiedIdentifierEntity) {
		//TODO
		return null;
	}
}
