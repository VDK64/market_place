package marketplace.validation;

import marketplace.validation.validatiors.TimeValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.exception.ItemErrorMessageConstants.WRONG_TIME_FORMAT;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeValidation.class)
public @interface Time {

    String message() default WRONG_TIME_FORMAT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
