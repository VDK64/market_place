package marketplace.restcontrollers;

import marketplace.dto.web.ResponseDto;
import marketplace.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Rest controller class which returns only items of current user.
 */
@RestController
@RequestMapping("webapi")
public class UserItemsRestController {

    @Autowired
    private ItemService service;

    /**
     * This method obtain client request to receive current user's items in JSON.
     *
     * @return ResponseDTO - data transfer object representing list of items.
     */
    @GetMapping("user-data/{email:.+}")
    public ResponseDto getRandomResponse(@PathVariable("email") String email)
            throws SQLException, IOException {
        return service.getCurrentUserItems(email);
    }

}