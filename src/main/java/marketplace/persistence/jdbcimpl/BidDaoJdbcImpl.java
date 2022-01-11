package marketplace.persistence.jdbcimpl;

import marketplace.dto.persistence.BidPersistenceDto;
import marketplace.entities.Bid;
import marketplace.persistence.BidDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static marketplace.queries.BidQueriesConstants.INSERT_INTO_BID;

/**
 * Data access object layer to implements working with database.
 */
@Component("BidDaoJdbcImpl")
public class BidDaoJdbcImpl implements BidDao {

    @Autowired
    private DataSource dataSource;

    /**
     * Do bid. Simple...
     *
     * @param bidDto which need to do.
     */
    @Override
    public void doBid(BidPersistenceDto bidDto) throws SQLException {
        final Bid bid = assembleEntity(bidDto);
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement bidPreparedStatement = connection
                     .prepareStatement(INSERT_INTO_BID)) {
            bidPreparedStatement.setFloat(1, bid.getAmount());
            bidPreparedStatement.setString(2, bid.getTimeStamp());
            bidPreparedStatement.setLong(3, bid.getUser().getId());
            bidPreparedStatement.setLong(4, bid.getItem().getId());
            bidPreparedStatement.execute();
        }
    }

    private Bid assembleEntity(BidPersistenceDto dto) {
        final Bid bid = new Bid();
        bid.setId(dto.getId());
        bid.setAmount(dto.getAmount());
        bid.setTimeStamp(dto.getTimeStamp());
        bid.setUser(dto.getUser());
        bid.setItem(dto.getItem());
        return bid;
    }

}
