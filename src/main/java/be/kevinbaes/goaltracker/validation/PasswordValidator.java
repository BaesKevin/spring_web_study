package be.kevinbaes.goaltracker.validation;

import be.kevinbaes.goaltracker.validation.annotation.StrongPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<StrongPassword, String> {
    public static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    @Override
    public void initialize(StrongPassword constraintAnnotation) {

    }
}
