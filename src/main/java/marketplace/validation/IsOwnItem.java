package marketplace.validation;

import marketplace.validation.validatiors.IsOwnItemValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.constants.ApplicationConstants.ITEM;
import static marketplace.constants.ApplicationConstants.USER;
import static marketplace.exception.BidErrorMessageConstants.WRONG_BIDDER;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsOwnItemValidation.class)
public @interface IsOwnItem {

    String message() default WRONG_BIDDER;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String user() default USER;

    String item() default ITEM;

}
