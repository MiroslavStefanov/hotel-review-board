package com.fmi.hotelreviewboard.common.validation.annotations;

import com.fmi.hotelreviewboard.common.validation.validators.constraintValidators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message() default "{com.fmi.hotelreviewboard.validation.Email.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
