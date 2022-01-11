package marketplace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static marketplace.constants.ApplicationConstants.SHOW_ITEMS_PAGE;

/**
 * Returning all items data servlet.
 */
@Controller
public class ItemsController {

    @GetMapping("/")
    public String getItems() {
        return SHOW_ITEMS_PAGE;
    }

    /**
     * This was an logout method which redirects on show items page.
     *
     * @return show items page
     */
    @PostMapping("/")
    public String logout() {
        return SHOW_ITEMS_PAGE;
    }

}