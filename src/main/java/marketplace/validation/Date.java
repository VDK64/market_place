package marketplace.validation;

import marketplace.validation.validatiors.DateValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.exception.ItemErrorMessageConstants.WRONG_DATE_FORMAT;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidation.class)
public @interface Date {

    String message() default WRONG_DATE_FORMAT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
