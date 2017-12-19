package com.aspect.queue.model;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ITPriorityBlockingQueueTest {
    final UniquePriorityBlockingQueue priorityBlockingQueue = new UniquePriorityBlockingQueue(1, new OrderComparator());

    @Before
    public void setUp() {

        priorityBlockingQueue.insert(new Order(new ClassifiedIdentifier(15l), System.currentTimeMillis()));
        priorityBlockingQueue.insert(new Order(new ClassifiedIdentifier(30l), System.currentTimeMillis()));
        priorityBlockingQueue.insert(new Order(new ClassifiedIdentifier(3l), System.currentTimeMillis()));
        priorityBlockingQueue.insert(new Order(new ClassifiedIdentifier(5l), System.currentTimeMillis()));
        priorityBlockingQueue.insert(new Order(new ClassifiedIdentifier(2l), System.currentTimeMillis()));
        priorityBlockingQueue.insert(new Order(new ClassifiedIdentifier(45l), System.currentTimeMillis()));
    }

    @Test
    public void add_items_insert_orders_in_sorted() {
        priorityBlockingQueue.stream().forEach(System.out::println);
        assertThat(priorityBlockingQueue, isInDescendingOrdering());
    }

    @Test
    public void searching_for_an_order_return_its_index() {
        assertEquals(java.util.Optional
                .ofNullable(priorityBlockingQueue.getIndexOf(new Order(new ClassifiedIdentifier(3l)))), Optional.of(4));
    }

    @Test
    public void average_wait_test() {
        Double average = priorityBlockingQueue.stream().mapToDouble(p -> p.getWaitingTime()).average().getAsDouble();
        assertTrue(average >= 0);
    }

    private Matcher<? super Collection<Order>> isInDescendingOrdering() {
        return new TypeSafeMatcher<Collection<Order>>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("describe the error has you like more");
            }

            @Override
            protected boolean matchesSafely(Collection<Order> item) {
                List<Order> orders = item.stream().collect(Collectors.toList());
                for (int i = 1; i < orders.size(); i++) {
                    if (orders.get(i).getPriority() == orders.get(i - 1).getPriority() &&
                            orders.get(i).getId().getRank() > orders.get(i - 1).getId().getRank() &&
                            orders.get(i).getTimestamp() <= orders.get(i - 1).getTimestamp()) {
                        return false;
                    }
                    if (orders.get(i).getPriority() > orders.get(i - 1).getPriority()) {
                        return false;
                    }

                }
                return true;
            }
        };
    }
}
