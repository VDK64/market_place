package marketplace.exception;

import lombok.Getter;
import marketplace.dto.web.ReturnUserDto;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * A class representing <code>UserException</code> exception.
 * Will be thrown if user data from the request not valid.
 */
@Getter
public class UserException extends RuntimeException {
    private ReturnUserDto returnUserDto;
    private ModelAndView modelAndView;
    private List<FieldError> errors;

    public UserException(String message, ReturnUserDto returnUserDto, ModelAndView modelAndView) {
        super(message);
        this.returnUserDto = returnUserDto;
        this.modelAndView = modelAndView;
    }

    public UserException(ModelAndView modelAndView, List<FieldError> errors) {
        this.modelAndView = modelAndView;
        this.errors = errors;
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}
