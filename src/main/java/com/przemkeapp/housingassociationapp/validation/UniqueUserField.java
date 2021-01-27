package com.przemkeapp.housingassociationapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUserFieldConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserField {
    String value();

    String message() default "this value exist in the database, type another one";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
