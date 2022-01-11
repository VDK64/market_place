package marketplace.validation;

import marketplace.validation.validatiors.IsExistValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.exception.UserErrorMessageConstants.ALREADY_EXISTS;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsExistValidation.class)
public @interface IsExist {

    String message() default ALREADY_EXISTS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
