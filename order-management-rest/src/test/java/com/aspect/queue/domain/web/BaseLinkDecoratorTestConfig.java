package com.aspect.queue.domain.web;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseLinkDecoratorTestConfig {

    protected ServletRequestAttributes attributes;
    @Mock protected HttpServletRequest mockRequest;

    @Before
    public void setupBase() {
        attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
        initializeMocks();
    }

    private void initializeMocks() {
        when(mockRequest.getScheme()).thenReturn("http");
        when(mockRequest.getServerPort()).thenReturn(80);
        when(mockRequest.getServerName()).thenReturn("aspect.com/ordermanager");
        when(mockRequest.getContextPath()).thenReturn("ordermanager");
        when(mockRequest.getRequestURI()).thenReturn("http://aspect.com/ordermanager");
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://aspect.com/ordermanager"));
        when(mockRequest.getQueryString()).thenReturn("");
        when(mockRequest.getServletPath()).thenReturn("");

    }
}
