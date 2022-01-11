package marketplace.validation.validatiors;

import marketplace.validation.IsLong;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static marketplace.constants.ApplicationConstants.EMPTY_STRING;

public class IsLongValidation implements ConstraintValidator<IsLong, String> {

    @Autowired
    private CommonValidator commonValidator;

    @Override
    public void initialize(IsLong constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.equals(EMPTY_STRING)) {
            return commonValidator.validateLong(value);
        }
        return true;
    }

}
