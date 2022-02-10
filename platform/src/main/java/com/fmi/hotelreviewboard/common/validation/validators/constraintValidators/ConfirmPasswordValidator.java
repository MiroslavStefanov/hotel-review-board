package com.fmi.hotelreviewboard.common.validation.validators.constraintValidators;

import com.fmi.hotelreviewboard.common.interfaces.PasswordConfirmable;
import com.fmi.hotelreviewboard.common.validation.annotations.ConfirmPasswordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordMatch, PasswordConfirmable> {

    @Override
    public boolean isValid(PasswordConfirmable passwordConfirmable, ConstraintValidatorContext constraintValidatorContext) {
        return passwordConfirmable.getPassword() != null && passwordConfirmable.getPassword().equals(passwordConfirmable.getConfirmPassword());
    }
}
