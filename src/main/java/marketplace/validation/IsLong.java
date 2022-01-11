package marketplace.validation;

import marketplace.validation.validatiors.IsLongValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.exception.ItemErrorMessageConstants.WRONG_LONG_VALUE;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsLongValidation.class)
public @interface IsLong {

    String message() default WRONG_LONG_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
