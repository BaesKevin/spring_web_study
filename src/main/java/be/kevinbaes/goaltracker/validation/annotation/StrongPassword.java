package be.kevinbaes.goaltracker.validation.annotation;


import be.kevinbaes.goaltracker.validation.PasswordValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PasswordValidator.class)
public @interface StrongPassword {
    // required parameters (see javadoc for @Constraint)
    String message() default "Password is not strong enough";

    Class[] groups() default {};

    Class[] payload() default {};
}
