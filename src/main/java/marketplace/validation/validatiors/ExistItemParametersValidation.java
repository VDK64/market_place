package marketplace.validation.validatiors;

import marketplace.validation.ExistItemParameters;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistItemParametersValidation implements ConstraintValidator<ExistItemParameters, Object> {
    private String itemId;
    private String startPrice;
    private String bidIncrement;
    private String stopTime;
    private String stopDate;

    @Override
    public void initialize(ExistItemParameters constraintAnnotation) {
        this.itemId = constraintAnnotation.itemId();
        this.startPrice = constraintAnnotation.startPrice();
        this.bidIncrement = constraintAnnotation.bidIncrement();
        this.stopTime = constraintAnnotation.stopTime();
        this.stopDate = constraintAnnotation.stopDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldItemId = new BeanWrapperImpl(value)
                .getPropertyValue(itemId);
        Object fieldStartPrice = new BeanWrapperImpl(value)
                .getPropertyValue(startPrice);
        Object fieldBidIncrement = new BeanWrapperImpl(value)
                .getPropertyValue(bidIncrement);
        Object fieldStopTime = new BeanWrapperImpl(value)
                .getPropertyValue(stopTime);
        Object fieldStopDate = new BeanWrapperImpl(value)
                .getPropertyValue(stopDate);
        return fieldItemId != null ||
                fieldNotNull(fieldStartPrice, fieldBidIncrement, fieldStopTime, fieldStopDate);
    }

    private boolean fieldNotNull(Object fieldStartPrice, Object fieldBidIncrement,
                                 Object fieldStopTime, Object fieldStopDate) {
        return fieldStartPrice != null && fieldBidIncrement != null &&
                fieldStopTime != null && fieldStopDate != null;
    }

}
