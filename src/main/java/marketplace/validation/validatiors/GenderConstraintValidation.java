package marketplace.validation.validatiors;

import marketplace.types.Gender;
import marketplace.validation.GenderConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderConstraintValidation implements ConstraintValidator<GenderConstraint, String> {

    @Override
    public void initialize(GenderConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Gender.valueOf(value);
        } catch (IllegalArgumentException exception) {
            return false;
        }
        return true;
    }

}
