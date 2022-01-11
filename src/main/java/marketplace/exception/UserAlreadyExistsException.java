package marketplace.exception;

import lombok.Getter;
import marketplace.dto.web.ReturnUserDto;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

/**
 * A class representing <code>UserAlreadyExistsException</code> exception.
 * Will be thrown if user with certain email already in base.
 */
@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private ReturnUserDto returnUserDto;
    private ModelAndView modelAndView;
    private FieldError error;


    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message,
                                      ReturnUserDto returnUserDto,
                                      ModelAndView modelAndView) {
        super(message);
        this.returnUserDto = returnUserDto;
        this.modelAndView = modelAndView;
    }

    public UserAlreadyExistsException(String message,
                                      ReturnUserDto returnUserDto,
                                      ModelAndView modelAndView,
                                      FieldError error) {
        super(message);
        this.returnUserDto = returnUserDto;
        this.modelAndView = modelAndView;
        this.error = error;
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
