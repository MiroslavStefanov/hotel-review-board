package com.fmi.hotelreviewboard.common.validation.validators.constraintValidators;

import com.fmi.hotelreviewboard.common.validation.annotations.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private int minLength, maxLength;
    private boolean digit, lower, upper, special;
    private String message;

    private static final Pattern digitPattern = Pattern.compile("[0-9]+");
    private static final Pattern lowerPattern = Pattern.compile("[a-z]+");
    private static final Pattern upperPattern = Pattern.compile("[A-Z]+");
    private static final Pattern specialPattern = Pattern.compile("[!@#$%^&*()_+<>?]+");

    @Override
    public void initialize(Password password) {
        minLength = password.minLength();
        maxLength = password.maxLength();
        digit = password.containsDigit();
        lower = password.containsLowercase();
        upper = password.containsUppercase();
        special = password.containsSpecialSymbol();
        message = password.message();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {

        if(s == null)
            return false;

        String msg = "{com.fmi.hotelreviewboard.validation.Password.";
        boolean isValid = true;

        if(s.length() < minLength || s.length() > maxLength) {

            msg += "length}";
            msg += " " + minLength + " and " + maxLength + " symbols";
            isValid = false;
        }  else {

            if(digit)
                isValid = isValid && digitPattern.matcher(s).find();
            if(lower)
                isValid = isValid && lowerPattern.matcher(s).find();
            if(upper)
                isValid = isValid && upperPattern.matcher(s).find();
            if(special)
                isValid = isValid && specialPattern.matcher(s).find();

            return isValid;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
        return isValid;
    }
}
