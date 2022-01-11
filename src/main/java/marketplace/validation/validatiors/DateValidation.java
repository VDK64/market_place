package marketplace.validation.validatiors;

import marketplace.validation.Date;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static marketplace.constants.ApplicationConstants.EMPTY_STRING;

public class DateValidation implements ConstraintValidator<Date, String> {

    @Autowired
    private CommonValidator commonValidator;

    @Override
    public void initialize(Date constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.equals(EMPTY_STRING)) {
            return commonValidator.validateDate(value);
        }
        return true;
    }

}
