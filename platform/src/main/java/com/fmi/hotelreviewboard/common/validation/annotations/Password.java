package com.fmi.hotelreviewboard.common.validation.annotations;

import com.fmi.hotelreviewboard.common.validation.validators.constraintValidators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default "{com.fmi.hotelreviewboard.validation.Password.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int minLength();
    int maxLength();
    boolean containsDigit() default false;
    boolean containsLowercase() default false;
    boolean containsUppercase() default false;
    boolean containsSpecialSymbol() default false;
}
