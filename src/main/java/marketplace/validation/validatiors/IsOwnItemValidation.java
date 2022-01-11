package marketplace.validation.validatiors;

import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.validation.IsOwnItem;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsOwnItemValidation implements ConstraintValidator<IsOwnItem, Object> {
    private String user;
    private String item;

    @Override
    public void initialize(IsOwnItem constraintAnnotation) {
        this.user = constraintAnnotation.user();
        this.item = constraintAnnotation.item();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        User fieldUser = (User) new BeanWrapperImpl(value)
                .getPropertyValue(user);
        Item fieldItem = (Item) new BeanWrapperImpl(value)
                .getPropertyValue(item);
        if (fieldUser != null && fieldItem != null) {
            return fieldUser.getId() != fieldItem.getUser().getId();
        }
        return false;
    }

}
