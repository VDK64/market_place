package marketplace.controllers;

import marketplace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static marketplace.constants.ApplicationConstants.LOGIN_PAGE;

/**
 * Login controller to handler login requests.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * Handle either POST or GET login requests.
     *
     * @param error   which could occur due login.
     * @param success exists if login was successfully.
     * @return view of login page.
     */
    @RequestMapping("login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "success", required = false) String success) {
        return LOGIN_PAGE;
    }

}
