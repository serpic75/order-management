package com.aspect.queue.model;

import com.aspect.queue.model.exceptions.DuplicatedOrderException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UniquePriorityBlockingQueueTest {

    final static long maximum = 9223372036854775807l;
    final static long minimum = 1l;

    @Mock
    private Order mockOrder;
    @Mock
    private Order mockOrder2;
    @Mock
    private Order mockOrder3;
    @Mock
    private Order mockOrder4;
    @Mock
    private ClassifiedIdentifier mockIdentifier;
    private UniquePriorityBlockingQueue queue;
    private Long genericId;
    private Long genericPriority;
    private long genericWaitingTime;
    private long genericTimestamp;
    Fixture fixture;

    @Before
    public void setUp() {
        queue = new UniquePriorityBlockingQueue();
        genericId = minimum + (long) (Math.random() * maximum);
        genericPriority = minimum + (long) (Math.random() * maximum);
        genericTimestamp = minimum + (long) (Math.random() * maximum);
        genericWaitingTime = minimum + (long) (Math.random() * maximum);
        fixture = new Fixture();
    }

    @Test
    public void a_valid_oder_is_successfully_inserted() {
        // Given
        initializedMocks();
        // When
        boolean isInserted = queue.insert(mockOrder);
        // Then
        Assert.assertTrue(isInserted);
    }

    @Test
    public void take_an_oder_successfully_remove_from_queue() throws Exception {
        // Given
        initializedMocks();
        initializedQueueWithOrder();
        // When
        Order order = queue.take();
        // Then
        assertEquals(mockOrder, order);
    }

    @Test
    public void max_priority_return_a_valids_number() throws Exception {
        // Given
        initializedMocks();
        initializedQueueWithOrders();
        // When
        Long max = queue.getMaxPriority();
        // Then
        assertNotNull(max);
    }

    @Test(expected = DuplicatedOrderException.class)
    public void add_an_existing_oder_should_throw_an_exception() throws Exception {
        // Given
        initializedMocks();
        initializedQueueWithOrder();
        // When
        queue.insert(mockOrder);
        // Then
    }

    private void initializedMocks() {
        initializedOrder();
        initializedMockClassifiedIdentifier();
    }

    private void initializedQueueWithOrder() {
        queue.insert(mockOrder);
    }

    private void initializedQueueWithOrders() {
        queue.add(mockOrder);
        queue.add(mockOrder2);
        queue.add(mockOrder3);
        queue.add(mockOrder4);
    }

    private void initializedMockClassifiedIdentifier() {
        when(mockIdentifier.getId()).thenReturn(genericId);
        when(mockIdentifier.getRank()).thenReturn(fixture.rank);
        when(mockIdentifier.getIdClass()).thenReturn(fixture.idClass);

    }

    private void initializedOrder() {
        when(mockOrder.getId()).thenReturn(mockIdentifier);
        when(mockOrder.getPriority()).thenReturn(genericPriority);
        when(mockOrder.getTimestamp()).thenReturn(genericTimestamp);
        when(mockOrder.getWaitingTime()).thenReturn(genericWaitingTime);
    }

    private class Fixture {
        private final int rank = 4;
        private final ClassifiedIdentifier.IdClass idClass = ClassifiedIdentifier.IdClass.MANAGMENTOVERRIDE;

        private Fixture() {

        }
    }
}
