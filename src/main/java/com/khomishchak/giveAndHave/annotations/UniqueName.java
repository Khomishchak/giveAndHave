package com.khomishchak.giveAndHave.annotations;

import com.khomishchak.giveAndHave.validators.UniqueNameValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueNameValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueName {

    String message() default "Name is already used";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
