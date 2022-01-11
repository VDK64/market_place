package marketplace.services;

import marketplace.dto.persistence.BidPersistenceDto;
import marketplace.dto.web.BidDto;
import marketplace.entities.Item;
import marketplace.entities.User;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Service which consists of bid business logic.
 */
public interface BidService {

    /**
     * Make bid and persist in database.
     *
     * @param user   which doing bid.
     * @param bidDto is data of user request for bid.
     * @param result storage of errors which may occur due validation.
     * @throws IOException    could be thrown if corresponding exception occur.
     * @throws SQLException   could be thrown if corresponding exception occur.
     * @throws ParseException could be thrown if corresponding exception occur.
     */
    void makeBid(User user, BidDto bidDto, BindingResult result)
            throws IOException, SQLException, ParseException;

    /**
     * Assembles item data transfer object to persistence layer.
     * @param item from which dto will assemble.
     * @return data transfer object.
     */
    BidPersistenceDto assembleBidDto(String nowDateString, float amount, Item item, User user);

}
