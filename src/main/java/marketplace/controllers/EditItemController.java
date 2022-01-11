package marketplace.controllers;

import marketplace.dto.web.ItemDto;
import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.services.ItemService;
import marketplace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;

import static marketplace.constants.ApplicationConstants.*;

/**
 * Servlet which routing edit item requests.
 */
@Controller
public class EditItemController {
    private static final String REDIRECT_TO_THE_ROOT = "redirect:/";

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    /**
     * Returns an item page of current item.
     *
     * @param itemId is an id of item which need to represent.
     * @param model  is a holder for model attributes.
     * @return name of page to represent.
     * @throws IOException  would be thrown if corresponding exception occur.
     * @throws SQLException would be thrown if corresponding exception occur.
     */
    @GetMapping("item")
    public String getItem(@RequestParam(value = ITEM_ID, required = false) String itemId, Model model)
            throws IOException, SQLException {
        final Item item = itemService.getItem(itemId);
        model.addAttribute(ITEM, item);
        model.addAttribute(DISABLED, EMPTY_STRING);
        if (item != null) {
            model.addAttribute(DISABLED, DISABLED);
            model.addAttribute(STOP_DATE_CAMEL_CASE, itemService.getDate(item));
            model.addAttribute(STOP_TIME_CAMEL_CASE, itemService.getTime(item));
        }
        return EDIT_ITEM_PAGE;
    }

    /**
     * Endpoint to handle post method for editing item.
     *
     * @param principal which editing item.
     * @param itemDto   is a dto with new parameters for editing.
     * @return name of view. In this case redirects to root page.
     * @throws IOException  would be thrown if corresponding exception occur.
     * @throws SQLException would be thrown if corresponding exception occur.
     */
    @PostMapping("item")
    public String postItem(@Valid ItemDto itemDto, BindingResult result,
                           @AuthenticationPrincipal User principal,
                           @RequestParam(name = "img", required = false) MultipartFile file)
            throws IOException, SQLException {
        itemService.saveItem(itemDto, result, file, principal);
        return REDIRECT_TO_THE_ROOT;
    }

}
