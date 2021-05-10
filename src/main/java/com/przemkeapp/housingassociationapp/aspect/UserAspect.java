package com.przemkeapp.housingassociationapp.aspect;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.exceptionhandling.UserNotFoundException;
import com.przemkeapp.housingassociationapp.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Array;
import java.security.Principal;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class UserAspect {

    @Pointcut("execution(* com.przemkeapp.housingassociationapp.service.UserServiceImpl.*(..))")
    private void pointcutForUserService() {
    }

    @Pointcut("execution(* com.przemkeapp.housingassociationapp.controller.*.*(..))")
    private void pointcutForController() {
    }

    @Around("pointcutForUserService()")
    public Object aroundUserDaoMethods(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Throwable e) {
            System.out.println("===============>>>>>>>>>>>>>>>>>> " + e.getMessage());
            System.out.println("===============>>>>>>>>>>>>>>>>>> " + e.getCause());
            e.printStackTrace();
        }
        return result;
    }
}
