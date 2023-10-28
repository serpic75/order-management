package com.aspect.queue.web;

import com.aspect.queue.model.errors.OrderRestError;
import com.aspect.queue.model.errorhandling.ErrorSanitizer;
import com.aspect.queue.model.exceptions.OrderException;
import com.aspect.queue.model.transformers.OrderRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionHandler<T extends OrderRepresentation> {

	@ExceptionHandler
	@ResponseBody ResponseEntity<OrderRestError> handleException(HttpServletRequest request, OrderException e) {
		OrderRestError restError = ErrorSanitizer.sanitizeRestError(new OrderRestError(e.getStatusCode(),e.getMessage(),
				request.getRequestURL().toString(), e.getErrorText()), request);
		return new ResponseEntity<>(restError, HttpStatus.valueOf(e.getStatusCode()));
	}
}
