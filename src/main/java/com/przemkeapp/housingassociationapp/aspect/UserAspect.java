package com.przemkeapp.housingassociationapp.aspect;

import com.przemkeapp.housingassociationapp.exceptionhandling.UserNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.security.Principal;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Order(0)
public class UserAspect {

    @Pointcut("execution(* com.przemkeapp.housingassociationapp.dao.UserDaoImpl.*(..))")
    private void pointcutForUserDao() {
    }

    @Pointcut("execution(* com.przemkeapp.housingassociationapp.controller.UserController.*(..))")
    private void pointcutForUserController() {
    }

    @Around("pointcutForUserDao()")
    public Object aroundUserDaoMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(e.getMessage());
        }

        return result;
    }
}
