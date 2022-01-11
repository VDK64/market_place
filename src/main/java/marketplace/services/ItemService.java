package marketplace.services;

import marketplace.dto.persistence.ItemPersistenceDto;
import marketplace.dto.web.ItemDto;
import marketplace.dto.web.ResponseDto;
import marketplace.entities.Item;
import marketplace.entities.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Service which consists of item business logic.
 */
public interface ItemService {

    /**
     * Returns response in JSON. Response consists of list of items.
     *
     * @return ResponseDTO is a representation of list of items.
     * @throws SQLException would be thrown if exception occur.
     */
    ResponseDto getAllItems() throws SQLException;

    /**
     * This method returns only current user's items data.
     *
     * @param email of user.
     * @return ResponseDTO is a representation of list of items.
     * @throws SQLException would be thrown if exception occur.
     * @throws IOException  would be thrown if exception occur.
     */
    ResponseDto getCurrentUserItems(String email) throws SQLException, IOException;

    /**
     * Find item by it's id.
     *
     * @param itemId is id of item which need to find.
     * @return found item.
     * @throws SQLException would be thrown if exception occur.
     */
    Item getItem(String itemId) throws SQLException, IOException;

    /**
     * Edit item method.
     *
     * @param itemDto   dto with item parameters received on server from user.
     * @param result    is a storage of errors which could occur due validate item.
     * @param file      represents image sending by user.
     * @param principal who send request to edit item.
     * @throws SQLException would be thrown if exception occur.
     * @throws IOException  would be thrown if exception occur.
     */
    void saveItem(ItemDto itemDto, BindingResult result, MultipartFile file, User principal)
            throws SQLException, IOException;

    String getDate(Item item);

    String getTime(Item item);

    ResponseDto getProperItems(String find) throws SQLException;

    /**
     * Assembles item data transfer object to persistence layer.
     * @param item from which dto will assemble.
     * @return data transfer object.
     */
    ItemPersistenceDto assembleItemDto(Item item);

}
