package marketplace.restcontrollers;

import marketplace.dto.web.ResponseDto;
import marketplace.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * Rest controller class which returns all items.
 */
@RestController
@RequestMapping("webapi")
public class ItemsRestController {

    @Autowired
    private ItemService service;

    /**
     * This method obtain client request to receive all items data in JSON.
     *
     * @return ResponseDTO - data transfer object representing list of items.
     */
    @GetMapping("all-data")
    public ResponseDto getAllItems() throws SQLException {
        return service.getAllItems();
    }

    @GetMapping("all-data/{find}")
    public ResponseDto getProperItems(@PathVariable(name = "find") String find) throws SQLException {
        return service.getProperItems(find);
    }

}