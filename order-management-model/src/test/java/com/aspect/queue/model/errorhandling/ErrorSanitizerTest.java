package com.aspect.queue.model.errorhandling;

import com.aspect.queue.model.errors.OrderRestError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class ErrorSanitizerTest {
    @Mock private HttpServletRequest mockHttpRequest;

    @Test public void sanitizeRestError_should_return_rest_error_for_non_head_requests() {
        //given
        when(mockHttpRequest.getMethod()).thenReturn("GET");
        OrderRestError OrderRestError = new OrderRestError(42, "test", "example", "error");

        //when
        OrderRestError sanitizedError = ErrorSanitizer.sanitizeRestError(OrderRestError, mockHttpRequest);

        //then
        assertEquals(OrderRestError, sanitizedError);
    }

    @Test public void sanitizeRestError_should_return_null_for_head_requests() {
        //given
        when(mockHttpRequest.getMethod()).thenReturn("HEAD");
        OrderRestError OrderRestError = new OrderRestError(42, "test", "example", "error");

        //when
        OrderRestError sanitizedError = ErrorSanitizer.sanitizeRestError(OrderRestError, mockHttpRequest);

        //then
        assertNull(sanitizedError);
    }
}