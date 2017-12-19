package com.aspect.queue.model.transformers;

import com.aspect.queue.model.exceptions.OrderException;

/**
 * Functional Interface which takes a given Entity and converts it into a Representation.
 *
 * @param <E> The input Entity.
 * @param <R> A Representation of the Entity.
 */
public interface Transformer<E, R> {

    /**
     * Transforms an entity to it's representation.
     *
     * @param entity
     * @return
     */
    R transform(E entity);

    /**
     * Transforms to a generic representation.
     *
     * @param number
     * @return
     */
    R transformValue(Number number);

    /**
     *
     *Extracts an entity from it's representation.
     *
     * @param representation
     * @return
     * @throws OrderException
     */
    E extract(R representation);


}
