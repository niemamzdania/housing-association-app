package com.przemkeapp.housingassociationapp.exceptionhandling;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Order(0)
public class UserExceptionHandler {

    @ExceptionHandler
    public ModelAndView handleException(UserNotFoundException exc) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "User Not Found Error",
                exc.getMessage(),
                System.currentTimeMillis()
        );

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", errorResponse);
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
