package com.aspect.queue.domain.web;

import com.aspect.queue.model.Order;
import com.aspect.queue.decorator.LinkDecorator;
import com.aspect.queue.model.OrderComparator;
import com.aspect.queue.model.UniquePriorityBlockingQueue;
import com.aspect.queue.model.exceptions.OrderException;
import com.aspect.queue.model.provider.BaseProvider;
import com.aspect.queue.model.transformers.OrderRepresentation;
import com.aspect.queue.model.transformers.OrderTransformer;
import com.aspect.queue.web.OrderController;
import com.aspect.queue.web.validator.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;

public class OrderControlleTest extends BaseLinkDecoratorTestConfig {

    final static long maximum=9223372036854775807l;
    final static long minimum=1l;

    @Mock private OrderRepresentation mockRepresentation;
    @Mock private LinkDecorator<OrderRepresentation> mockDecorator;
    @Mock private Validator<OrderRepresentation> validator;
    @Mock private BaseProvider<Order> mockProvider;
    @Mock private OrderTransformer mockTransformer;
    @Mock private OrderRepresentation mockRepresentationFromTransformer;
    @Mock private OrderRepresentation mockRepresentationFromDecorator;
    @Mock private Order mockFromTransformer;
    @Mock private Order mockFromProvider;
    @Mock private Order mockOrder;
    @Mock private HttpServletRequest mockHttpServletRequest;
    @Mock private UniquePriorityBlockingQueue mockQueue;
    private String mockId = String.valueOf(System.currentTimeMillis());
    private String mockTimeStamp = String.valueOf(minimum+ (long)(Math.random() * maximum));


    private Fixture fixture;
    private OrderController target;

    @Before
    public void setup() {
        fixture = new Fixture();
        target = new OrderController(mockDecorator, validator, mockProvider, mockTransformer);
    }

    @Test
    public void create_order_success_should_return_appropriate_response_code() {
        // Given
        mocksInitialized( null);

        // When
        ResponseEntity<OrderRepresentation> repr = target.createOrder(mockId, mockTimeStamp);

        // Then
        assertThat(repr.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    public void create_order_success_should_return_appropriate_response_entity() {
        // Given
        mocksInitialized(null);

        // When
        ResponseEntity<OrderRepresentation> repr = target.createOrder(mockId, mockTimeStamp);

        // Then

        Assert.assertEquals(repr.getBody(), mockRepresentationFromDecorator);

    }

    @Test
    public void delete_topOrder_success_should_return_appropriate_response() {
        // Given
        mocksInitialized( null);

        // When
        ResponseEntity<OrderRepresentation> repr = target.lookupTopOrder();
        // Then
        assertThat(repr.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void delete_an_order_success_should_return_appropriate_response() {
        // Given
        mocksInitialized( null);

        // When
        ResponseEntity<OrderRepresentation> repr = target.lookupOrder(mockId);
        // Then
        assertThat(repr.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void list_all_element_should_return_appropriate_response_code(){
        // Given
        mocksInitialized( null);

        // When
        ResponseEntity<List<OrderRepresentation>> repr = target.findAllOrders();

        // Then
        assertThat(repr.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void setup_order() throws IOException, URISyntaxException {
        final URL requestURL = new URL(
                "https://test2.me.aspect.com/ordermanagement");

        StringBuffer requestURLBuffer = new StringBuffer(requestURL.toString());

        when(mockHttpServletRequest.getServletPath()).thenReturn("ordermanagement");
        when(mockHttpServletRequest.getContextPath()).thenReturn("/");
        when(mockHttpServletRequest.getRequestURL()).thenReturn(requestURLBuffer);
        when(mockHttpServletRequest.getRequestURI()).thenReturn(requestURL.toURI().toString());

        ResponseEntity<?> response = target.createOrder(mockId, mockTimeStamp);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private void mocksInitialized( Throwable orderException) {
        mockInputRepresentationInitialized();
        mockDecoratorInitialized();
        mockTransformerInitialized();

        mockProviderInitialized(orderException);
        mockQueueInitialized();
    }

    private void mockQueueInitialized() {
        when(mockQueue.add(mockOrder)).thenReturn(true);
        when(mockQueue.contains(mockOrder)).thenReturn(true);
        when(mockQueue.getIndexOf(mockOrder)).thenReturn(anyInt());
        when(mockQueue.remove(mockOrder)).thenReturn(true);
    }

    private void mockInputRepresentationInitialized() {
        when(mockRepresentation.getRepresentationId()).thenReturn(fixture.id);
    }

    private void mockDecoratorInitialized() {
        when(mockDecorator.decorate(mockRepresentationFromTransformer)).thenReturn(mockRepresentationFromDecorator);
    }

    private void mockTransformerInitialized() {
        when(mockTransformer.extract(anyObject())).thenReturn(mockFromTransformer);
        when(mockTransformer.transform(mockFromProvider)).thenReturn(mockRepresentationFromTransformer);
    }

    private void mockProviderInitialized(Throwable t) throws OrderException {
        if (t == null) {
            when(mockProvider.create(mockFromTransformer)).thenReturn(mockFromProvider);
            when(mockProvider.find(any(Order.class))).thenReturn(mockFromProvider);
            when(mockProvider.findTop()).thenReturn(Optional.of(mockFromProvider));

        } else {
            when(mockProvider.create(same(mockFromTransformer))).thenThrow(t);
            when(mockProvider.find(any(Order.class))).thenThrow(t);
            when(mockProvider.findTop()).thenThrow(t);
        }

    }

    private class Fixture {
        private final Long id = 17l;

        private Fixture() {

        }
    }
}
