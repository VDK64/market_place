package marketplace.persistence;

import marketplace.dto.persistence.BidPersistenceDto;

import java.sql.SQLException;

/**
 * Data access object interface with methods relate with bid.
 */
public interface BidDao {

    void doBid(BidPersistenceDto bidDto) throws SQLException;

}
