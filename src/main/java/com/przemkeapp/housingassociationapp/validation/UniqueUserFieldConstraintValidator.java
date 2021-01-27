package com.przemkeapp.housingassociationapp.validation;

import com.przemkeapp.housingassociationapp.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserFieldConstraintValidator implements ConstraintValidator<UniqueUserField, String> {

    private String field;

    @Autowired
    private UserDao userDao;

    public void initialize(UniqueUserField constraint) {
        field = constraint.value();
    }

    public boolean isValid(String theValue, ConstraintValidatorContext context) {

        return userDao.checkIfUnique(field, theValue);
    }
}
