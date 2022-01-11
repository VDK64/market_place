package marketplace.services;

import java.sql.SQLException;

/**
 * Service which consists of image business logic.
 */
public interface ImageService {

    /**
     * Get the item image from database by item id.
     *
     * @param id is an id of item which image need to obtain.
     * @return byte array making up the image.
     * @throws SQLException would be thrown if exception occur.
     */
    byte[] getImageById(long id) throws SQLException;

}
