package com.aspect.queue.decorator;

import org.springframework.hateoas.ResourceSupport;

public interface LinkDecorator<T extends ResourceSupport> {
    /**
     * Add HATEOAS links to the input Resource in the input Domain.
     */
    T decorate(T resource);
}
