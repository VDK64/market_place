package marketplace.validation;

import marketplace.validation.validatiors.IsFloatValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.exception.ItemErrorMessageConstants.WRONG_FLOAT_VALUE;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsFloatValidation.class)
public @interface IsFloat {

    String message() default WRONG_FLOAT_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
