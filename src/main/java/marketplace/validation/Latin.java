package marketplace.validation;

import marketplace.validation.validatiors.LatinValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.exception.UserErrorMessageConstants.CYRILLIC_SYMBOLS_NOT_FOUND;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LatinValidation.class)
public @interface Latin {

    String message() default CYRILLIC_SYMBOLS_NOT_FOUND;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
