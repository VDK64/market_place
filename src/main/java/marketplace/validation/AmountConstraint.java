package marketplace.validation;

import marketplace.validation.validatiors.AmountConstraintValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.constants.ApplicationConstants.AMOUNT;
import static marketplace.constants.ApplicationConstants.ITEM;
import static marketplace.exception.BidErrorMessageConstants.WRONG_AMOUNT;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AmountConstraintValidation.class)
public @interface AmountConstraint {

    String message() default WRONG_AMOUNT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String amount() default AMOUNT;

    String item() default ITEM;

}
