package marketplace.validation.validatiors;

import marketplace.entities.Item;
import marketplace.validation.AmountConstraint;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountConstraintValidation implements ConstraintValidator<AmountConstraint, Object> {
    private String amount;
    private String item;

    @Override
    public void initialize(AmountConstraint constraintAnnotation) {
        this.amount = constraintAnnotation.amount();
        this.item = constraintAnnotation.item();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        final Float fieldAmount = (Float) new BeanWrapperImpl(value)
                .getPropertyValue(amount);
        final Item fieldItem = (Item) new BeanWrapperImpl(value)
                .getPropertyValue(item);
        if (fieldAmount != null && fieldItem != null) {
            final float minimumAmountToBid = BigDecimal.valueOf(fieldItem.getBestOffer() + fieldItem.getBidInc())
                    .setScale(2, RoundingMode.HALF_EVEN).floatValue();
            return fieldAmount >= minimumAmountToBid;
        }
        return false;
    }

}
