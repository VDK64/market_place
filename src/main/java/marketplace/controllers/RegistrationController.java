package marketplace.controllers;

import marketplace.dto.web.UserDto;
import marketplace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;

import static marketplace.constants.ApplicationConstants.REGISTRATION_PAGE;

/**
 * Controller for handling registration requests.
 */
@Controller
public class RegistrationController {
    private static final String SUCCESS_REDIRECTION = "redirect:/login?success=1";

    @Autowired
    private UserService userService;

    /**
     * Represents a registration form.
     *
     * @return view of a registration form.
     */
    @GetMapping("registration")
    public String getForm() {
        return REGISTRATION_PAGE;
    }

    /**
     * Handle post request to register user.
     *
     * @param userDto      is data of future user.
     * @param result       is a result of user validation.
     * @param modelAndView is holder of attributes.
     * @return redirection url to a login page with success parameter.
     * @throws IOException  would be thrown if corresponding exception occur.
     * @throws SQLException would be thrown if corresponding exception occur.
     */
    @PostMapping("registration")
    public String register(@Valid UserDto userDto, BindingResult result,
                           ModelAndView modelAndView)
            throws IOException, SQLException {
        modelAndView.setViewName(REGISTRATION_PAGE);
        userService.saveUser(userDto, result, modelAndView);
        return SUCCESS_REDIRECTION;
    }

}
