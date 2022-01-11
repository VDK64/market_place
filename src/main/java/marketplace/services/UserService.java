package marketplace.services;

import marketplace.dto.web.UserDto;
import marketplace.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This is a service to do business user logic.
 * Extends <code>UserDetailsService</code> to implement security logic.
 */
public interface UserService extends UserDetailsService {

    /**
     * Method for saving user in database
     *
     * @param userDto      is an user which need to save in database.
     * @param result       consists of errors if occurred when validate user.
     * @param modelAndView represents page view model
     * @throws SQLException would be thrown if exception occur.
     * @throws IOException  would be thrown if exception occur.
     */
    void saveUser(UserDto userDto, BindingResult result,
                  ModelAndView modelAndView) throws SQLException, IOException;

    /**
     * Gets user from a database by email.
     *
     * @param email need to obtain user from database.
     * @return obtained user.
     * @throws IOException  would be thrown if exception occur.
     * @throws SQLException would be thrown if exception occur.
     */
    User getUser(String email) throws IOException, SQLException;

}
