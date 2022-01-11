package marketplace.validation.validatiors;

import marketplace.entities.Item;
import marketplace.validation.IsClosed;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static marketplace.constants.ApplicationConstants.DATE_FORMAT;

public class IsClosedValidation implements ConstraintValidator<IsClosed, Object> {
    private static final SimpleDateFormat DATE_PARSER;

    static {
        DATE_PARSER = new SimpleDateFormat(DATE_FORMAT);
    }

    @Override
    public void initialize(IsClosed constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value != null) {
            Item item = (Item) value;
            final Date nowDate = new Date(System.currentTimeMillis() + 10000);
            try {
                Date parseDate = DATE_PARSER.parse(item.getStopDate());
                return !parseDate.before(nowDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
