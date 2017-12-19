package com.aspect.queue.domain.web;

import com.aspect.queue.model.Order;
import com.aspect.queue.decorator.LinkDecorator;
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
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;

public class OrderControlleTest extends BaseLinkDecoratorTestConfig {

    @Mock private OrderRepresentation mockRepresentation;
    @Mock private LinkDecorator<OrderRepresentation> mockDecorator;
    @Mock private Validator<OrderRepresentation> validator;
    @Mock private BaseProvider<Order> mockProvider;
    @Mock private OrderTransformer mockTransformer;
    @Mock private OrderRepresentation mockRepresentationFromTransformer;
    @Mock private OrderRepresentation mockRepresentationFromDecorator;
    @Mock private Order mockFromTransformer;
    @Mock private Order mockFromProvider;
    @Mock private HttpServletRequest mockHttpServletRequest;


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
        ResponseEntity<OrderRepresentation> repr = target.createOrder(mockRepresentation, mockRequest);

        // Then
        assertThat(repr.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    public void create_order_success_should_return_appropriate_response_entity() {
        // Given
        mocksInitialized(null);

        // When
        ResponseEntity<OrderRepresentation> repr = target.createOrder(mockRepresentation, mockRequest);

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
    public void setup_order() throws IOException, URISyntaxException {
        final URL requestURL = new URL(
                "https://test2.me.aspect.com/ordermanagement");

        StringBuffer requestURLBuffer = new StringBuffer(requestURL.toString());

        when(mockHttpServletRequest.getServletPath()).thenReturn("ordermanagement");
        when(mockHttpServletRequest.getContextPath()).thenReturn("/");
        when(mockHttpServletRequest.getRequestURL()).thenReturn(requestURLBuffer);
        when(mockHttpServletRequest.getRequestURI()).thenReturn(requestURL.toURI().toString());

        ResponseEntity<?> response = target.createOrder(mockRepresentation, mockHttpServletRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private void mocksInitialized( Throwable orderException) {
        mockInputRepresentationInitialized();
        mockDecoratorInitialized();
        mockTransformerInitialized();

        mockProviderInitialized(orderException);
    }

    private void mockInputRepresentationInitialized() {
        when(mockRepresentation.getRepresentationId()).thenReturn(fixture.id);
    }

    private void mockDecoratorInitialized() {
        when(mockDecorator.decorate(mockRepresentationFromTransformer)).thenReturn(mockRepresentationFromDecorator);
    }

    private void mockTransformerInitialized() {
        when(mockTransformer.extract(mockRepresentation)).thenReturn(mockFromTransformer);
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
