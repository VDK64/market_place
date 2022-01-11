package marketplace.validation.validatiors;

import marketplace.validation.IsFloat;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static marketplace.constants.ApplicationConstants.EMPTY_STRING;

public class
IsFloatValidation implements ConstraintValidator<IsFloat, String> {

    @Autowired
    private CommonValidator commonValidator;

    @Override
    public void initialize(IsFloat constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.equals(EMPTY_STRING)) {
            return commonValidator.validateFloat(value);
        }
        return true;
    }

}
