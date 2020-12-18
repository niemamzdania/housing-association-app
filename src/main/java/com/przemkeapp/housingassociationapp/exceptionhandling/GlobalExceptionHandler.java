package com.przemkeapp.housingassociationapp.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ModelAndView handleException(NumberFormatException exc) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", errorResponse);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView handleException(AccessDeniedException exc) {
        ModelAndView view = new ModelAndView();
        view.setViewName("access-denied");
        return view;
    }

    @ExceptionHandler
    public ModelAndView handleException(NoHandlerFoundException exc) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", errorResponse);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView handleException(Exception exc) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", errorResponse);
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
