package com.aspect.queue.model.errorhandling;

import com.aspect.queue.model.OrderRestError;
import com.aspect.queue.model.exceptions.OrderException;
import com.aspect.queue.model.exceptions.OrderInvalidValueException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static com.aspect.queue.model.errorhandling.ErrorSanitizer.getSanitizedErrorMessage;
import static com.aspect.queue.model.errorhandling.ErrorSanitizer.sanitizeRestError;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    ResponseEntity<OrderRestError> handleException(HttpServletRequest request, OrderException e) {

        OrderRestError restError = sanitizeRestError(new OrderRestError(e.getStatusCode(), e.getErrorText(),
                request.getRequestURL().toString()), request);

        return new ResponseEntity<>(restError, HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler
    @ResponseBody
    ResponseEntity<OrderRestError> handleException(HttpServletRequest request,
            IllegalArgumentException e) {
        return handleException(request, new OrderException("id", e));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    OrderRestError handleException(HttpServletRequest request, HttpMessageNotReadableException e) {
        //On HttpMessageNotReadableException the important information is usually stored in the cause of the exception
        Throwable cause = e.getCause() != null ? e.getCause() : e;
        return sanitizeRestError(new OrderRestError(HttpStatus.BAD_REQUEST.value(), getSanitizedErrorMessage(cause),
                request.getRequestURL().toString()), request);
    }

    @ExceptionHandler
    @ResponseBody
    ResponseEntity<OrderRestError> handleException(
            HttpServletRequest request, TypeMismatchException exception) {
        OrderException e = new OrderInvalidValueException("Request");
        //TypeMismatchException contains the original conversion exception. Extract the exception and return an error
        if (exception.getCause() != null) {
            Throwable tmpThrowable = exception.getCause();
            if (tmpThrowable.getCause() instanceof OrderException) {
                e = (OrderException) tmpThrowable.getCause();
            }
        }

        return handleException(request, e);
    }
}
