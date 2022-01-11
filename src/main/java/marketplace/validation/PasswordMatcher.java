package marketplace.validation;

import marketplace.validation.validatiors.PasswordMatcherValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.exception.UserErrorMessageConstants.DIFFERENT_PASSWORDS;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatcherValidation.class)
public @interface PasswordMatcher {

    String message() default DIFFERENT_PASSWORDS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String fieldMatch();

}
