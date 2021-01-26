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
    public String value();

    public String message() default "this value exist in the database, type another one";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
