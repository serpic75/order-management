package com.aspect.queue.decorator;

import org.springframework.hateoas.RepresentationModel;

public interface LinkDecorator<T extends RepresentationModel> {
    /**
     * Add HATEOAS links to the input Resource in the input Domain.
     */
    T decorate(T resource);
}
