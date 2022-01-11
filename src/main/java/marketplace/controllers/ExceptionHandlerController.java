package marketplace.controllers;

import marketplace.exception.BidException;
import marketplace.exception.ItemException;
import marketplace.exception.UserAlreadyExistsException;
import marketplace.exception.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static marketplace.constants.ApplicationConstants.ERROR_PAGE;
import static marketplace.constants.ApplicationConstants.WRONG_DATA;
import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * Common exception handler for all application controllers.
 */
@ControllerAdvice
public class ExceptionHandlerController {
    private final Logger logger = LogManager.getLogger(ExceptionHandlerController.class);
    private final Logger registrationLogger = getLogger(RegistrationController.class);
    private final Logger editItemLogger = getLogger(EditItemController.class);
    private final Logger bidLogger = getLogger(BidController.class);
    private static final String EXISTS = "exists";
    private static final String REGISTRATION_ERROR = "Error was occur due the registration in field: {}. The Error is: {}";
    private static final String EDIT_ITEM_ERROR = "Error was occur due the editing item in field: {}. The Error is: {}";
    private static final String BIDDING_ERROR = "Error was occur due the bidding in field: {}. The Error is: {}";

    /**
     * Handler which handle <code>IOException</code>,
     * <code>SQLException</code> or <code>ParseException</code>.
     *
     * @param exception which was previously thrown.
     * @return view of error page.
     */
    @ExceptionHandler({IOException.class, SQLException.class, ParseException.class})
    public String globalErrorHandler(Exception exception) {
        logger.info(exception);
        return ERROR_PAGE;
    }

    /**
     * Handles an exception of current type {@link UserAlreadyExistsException}.
     *
     * @param exception which was threw.
     * @return view with model attributes.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ModelAndView registrationErrorAlreadyExists(UserAlreadyExistsException exception) {
        final FieldError error = exception.getError();
        final ModelAndView modelAndView = exception.getModelAndView();
        modelAndView.addObject("userDto", exception.getReturnUserDto());
        modelAndView.addObject(EXISTS, true);
        registrationLogger.info(REGISTRATION_ERROR, error.getField(), error.getDefaultMessage());
        return modelAndView;
    }

    /**
     * Handles an exception of current type {@link UserException}.
     *
     * @param exception which was threw.
     * @return error page.
     */
    @ExceptionHandler(UserException.class)
    public String wrongDataExceptionHandler(UserException exception) {
        exception.getErrors().forEach(error ->
                registrationLogger.info(REGISTRATION_ERROR, error.getField(), error.getDefaultMessage()));
        return WRONG_DATA;
    }

    /**
     * Handles an exception of current type {@link ItemException}.
     *
     * @param exception which was threw.
     * @return error page.
     */
    @ExceptionHandler(ItemException.class)
    public String registrationErrorAlreadyExists(ItemException exception) {
        final List<FieldError> errors = exception.getErrors();
        errors.forEach(error -> editItemLogger.info(EDIT_ITEM_ERROR, error.getField(), error.getDefaultMessage()));
        return WRONG_DATA;
    }

    /**
     * Handles an exception of current type {@link BidException}.
     *
     * @param exception which was threw.
     * @return error page.
     */
    @ExceptionHandler(BidException.class)
    public String wrongDataExceptionHandler(BidException exception) {
        exception.getErrors().forEach(error ->
                bidLogger.info(BIDDING_ERROR, error.getField(), error.getDefaultMessage()));
        return WRONG_DATA;
    }

}
