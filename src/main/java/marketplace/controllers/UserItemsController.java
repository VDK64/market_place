package marketplace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static marketplace.constants.ApplicationConstants.MY_ITEMS_PAGE;

/**
 * Controller for handling requests for items of current user.
 */
@Controller
public class UserItemsController {

    @GetMapping("myItems")
    public String getUserItems() {
        return MY_ITEMS_PAGE;
    }

}