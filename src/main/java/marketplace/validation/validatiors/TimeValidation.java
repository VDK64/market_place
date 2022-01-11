package marketplace.validation.validatiors;

import marketplace.validation.Time;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static marketplace.constants.ApplicationConstants.EMPTY_STRING;

public class TimeValidation implements ConstraintValidator<Time, String> {

    @Autowired
    private CommonValidator commonValidator;

    @Override
    public void initialize(Time constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.equals(EMPTY_STRING)) {
            return commonValidator.validateTime(value);
        }
        return true;
    }

}
