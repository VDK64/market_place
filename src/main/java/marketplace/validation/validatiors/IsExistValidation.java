package marketplace.validation.validatiors;

import marketplace.persistence.UserDao;
import marketplace.validation.IsExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.sql.SQLException;

public class IsExistValidation implements ConstraintValidator<IsExist, String> {

    @Autowired
    @Qualifier("UserDaoHibernateImpl")
    private UserDao userDao;

    @Override
    public void initialize(IsExist constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            final long count = userDao.countUserByEmail(value);
            if (count > 0) {
                return false;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}