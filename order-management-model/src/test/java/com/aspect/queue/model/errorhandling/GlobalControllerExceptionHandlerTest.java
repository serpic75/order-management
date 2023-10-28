package com.aspect.queue.model.errorhandling;

import com.aspect.queue.model.errors.OrderRestError;
import com.aspect.queue.model.exceptions.OrderException;
import com.aspect.queue.model.exceptions.OrderInvalidValueException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GlobalControllerExceptionHandlerTest {
    private GlobalControllerExceptionHandler handler;

    @Mock private OrderInvalidValueException mockOpFailedException;
    @Mock private HttpServletRequest mockHttpRequest;

    private OrderException orderException;

    private Fixture fixture;

    @Before
    public void setup() {
        handler = new GlobalControllerExceptionHandler();
        fixture = new Fixture();
        orderException = new OrderException();
    }

    @Test
    public void exception_handler_for_internal_exception_should_contain_url() throws Exception {
        // Given
        mocksInitialized();

        // When
        ResponseEntity<OrderRestError> actualError = handler.handleException(mockHttpRequest, orderException);

        // Then
        assertEquals(actualError.getBody().getUrl(), fixture.requestUrl);
    }

    @Test
    public void exception_handler_for_internal_exception_should_show_internal_server_error() throws Exception {
        // Given
        mocksInitialized();

        // When
        ResponseEntity<OrderRestError> actualError = handler.handleException(mockHttpRequest, orderException);

        // Then
        assertEquals(actualError.getStatusCode(), fixture.internalServerError);
    }

    private void mocksInitialized() {
        given(mockOpFailedException.getLocalizedMessage()).willReturn(fixture.opFailedMessage);
        given(mockHttpRequest.getRequestURL()).willReturn(new StringBuffer(fixture.requestUrl));

    }

    private class Fixture {
        private final String opFailedMessage = "OpFailed";
        private final String requestUrl = "requestUrl";

        private final HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
