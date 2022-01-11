package marketplace.validation.validatiors;

import marketplace.validation.Latin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatinValidation implements ConstraintValidator<Latin, String> {
    private static final String CYRILLIC_REGEX = "[A-Za-z]+";

    @Override
    public void initialize(Latin constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(CYRILLIC_REGEX);
    }

}
