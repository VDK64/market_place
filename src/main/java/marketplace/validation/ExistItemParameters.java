package marketplace.validation;

import marketplace.validation.validatiors.ExistItemParametersValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.constants.ApplicationConstants.*;
import static marketplace.exception.ItemErrorMessageConstants.WRONG_ITEM_PARAMETERS;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistItemParametersValidation.class)
public @interface ExistItemParameters {

    String message() default WRONG_ITEM_PARAMETERS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String itemId() default ITEM_ID;

    String startPrice() default START_PRICE_CAMEL_CASE;

    String bidIncrement() default BID_INCREMENT;

    String stopTime() default STOP_TIME_CAMEL_CASE;

    String stopDate() default STOP_DATE_CAMEL_CASE;

}
