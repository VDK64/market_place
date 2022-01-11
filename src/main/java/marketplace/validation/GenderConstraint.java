package marketplace.validation;

import marketplace.validation.validatiors.GenderConstraintValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static marketplace.exception.UserErrorMessageConstants.INVALID_GENDER;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderConstraintValidation.class)
public @interface GenderConstraint {

    String message() default INVALID_GENDER;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}



