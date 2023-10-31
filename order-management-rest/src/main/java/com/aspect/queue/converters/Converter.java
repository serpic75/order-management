package com.aspect.queue.converters;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Converter<T, U> {

	private final Function<T, U> fromBean;
	private final Function<U, T> fromEntity;

	public Converter(final Function<T, U> fromBean, final Function<U, T> fromEntity) {
		this.fromBean = fromBean;
		this.fromEntity = fromEntity;
	}

	public final U convertFromBean(final T bean) {
		return fromBean.apply(bean);
	}

	public final T convertFromEntity(final U entity) {
		return fromEntity.apply(entity);
	}

	public final List<U> createFromBeans(final Collection<T> dtos) {
		return dtos.stream().map(this::convertFromBean).collect(Collectors.toList());
	}

	public final List<T> createFromEntities(final Collection<U> entities) {
		return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
	}
}
