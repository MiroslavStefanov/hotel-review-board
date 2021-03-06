package com.fmi.hotelreviewboard.common.validation.annotations;

import com.fmi.hotelreviewboard.common.validation.validators.constraintValidators.ConfirmPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmPasswordValidator.class)
public @interface ConfirmPasswordMatch {
    String message() default "{com.fmi.hotelreviewboard.validation.Password.confirm}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
