package marketplace.validation;

import marketplace.validation.validatiors.IsClosedValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.exception.BidErrorMessageConstants.BID_CLOSED;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsClosedValidation.class)
public @interface IsClosed {

    String message() default BID_CLOSED;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
