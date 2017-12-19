package com.aspect.queue.model.provider;

import com.aspect.queue.model.exceptions.OrderException;

import java.util.List;
import java.util.Optional;

/**
 * Generic domain model provider for simple CRUD operations.
 *
 * @param <T> The Type of the Provider.
 */
public interface BaseProvider<T> {
    T find(T key);

    /**
     * returns a List of Objects of Type T
     *
     * @return
     * @throws OrderException
     */
    List<T> findAll();

    /**
     *  deletes an Object of a given type T
     *
     * @param id
     * @return
     * @throws OrderException
     */
    boolean delete(Long id);

    /**
     * for incoming data create a new Object of a given type T
     *
     * @param t
     * @return
     * @throws OrderException
     */
    T create(T t);

    /**
     * find an Object of a given type T at the top of a queue
     *
     * @return
     * @throws OrderException
     */
    Optional<T> findTop();

    /**
     * find the positio of an Object of a given type T
     *
     * @param t
     * @return
     * @throws OrderException
     */
    Integer findPosition(T t);

    /**
     * Calculate the average waiting time
     *
     * @return
     * @throws OrderException
     */
    Double averageWaitingTime();

    /**
     * Check if an Object of given tType T is present
     *
     * @param t
     * @return
     */
    Boolean isPresent(T t);
}
