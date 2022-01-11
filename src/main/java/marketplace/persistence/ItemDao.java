package marketplace.persistence;

import marketplace.dto.persistence.ItemPersistenceDto;
import marketplace.entities.Item;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Data access object interface with methods relate with item.
 */
public interface ItemDao {

    /**
     * Save item in database.
     *
     * @param itemDto        which need to save.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    void saveItem(ItemPersistenceDto itemDto) throws SQLException, IOException;

    /**
     * Get all items from a database.
     *
     * @return list of items.
     * @throws SQLException will thrown when sql exception occurred.
     */
    List<Item> getAllItems() throws SQLException;

    /**
     * Method for receiving items of current user.
     *
     * @param email of a user (principal).
     * @return list of items from a database.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    List<Item> getCurrentUserItems(String email) throws SQLException, IOException;

    /**
     * Find item for it id in a database.
     *
     * @param itemId is the id of item.
     * @return item, wrapped in <code>Optional</code> class.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    Optional<Item> findItemById(Long itemId) throws SQLException, IOException;

    List<Item> getProperItems(String find) throws SQLException;

    byte[] getItemImageById(long id) throws SQLException;

}
