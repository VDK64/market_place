package marketplace.persistence.mappers.impl;

import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.persistence.mappers.ItemMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static marketplace.constants.ApplicationConstants.*;

/**
 * Implementation of {@link ItemMapper} interface.
 * Needs to map entities from database on POJO.
 */
@Component
public class ItemMapperImpl implements ItemMapper {
    /**
     * Map item from database on POJO class.
     *
     * @param rs result set after sql query execution.
     * @return item.
     * @throws SQLException will be thrown if exception occur.
     */
    @Override
    public Item mapItem(ResultSet rs) throws SQLException {
        final Item item = new Item();
        while (rs.next()) {
            item.setId(rs.getInt(ID));
            item.setTitle(rs.getString(TITLE));
            item.setDescription(rs.getString(DESCRIPTION));
            item.setSeller(rs.getString(SELLER));
            item.setStartPrice(rs.getFloat(START_PRICE));
            item.setBidInc(rs.getFloat(BID_INCREMENT_SHORT));
            item.setBestOffer(rs.getFloat(BEST_OFFER));
            item.setBidder(rs.getString(BIDDER));
            item.setStopDate(rs.getString(STOP_DATE));
            final User user = new User();
            user.setId(rs.getLong(USER_ID));
            user.setEmail(rs.getString(EMAIL));
            item.setUser(user);
        }
        rs.close();
        return item;
    }

    /**
     * Map items from database on list of POJO classes.
     *
     * @param rs result set after sql query execution.
     * @return list of items.
     * @throws SQLException will be thrown if exception occur.
     */
    @Override
    public List<Item> mapItems(ResultSet rs) throws SQLException {
        List<Item> items = new ArrayList<>();
        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getInt(ID));
            item.setTitle(rs.getString(TITLE));
            item.setDescription(rs.getString(DESCRIPTION));
            item.setSeller(rs.getString(SELLER));
            item.setStartPrice(rs.getFloat(START_PRICE));
            item.setBidInc(rs.getFloat(BID_INCREMENT_SHORT));
            item.setBestOffer(rs.getFloat(BEST_OFFER));
            item.setBidder(rs.getString(BIDDER));
            item.setStopDate(rs.getString(STOP_DATE));
            final User user = new User();
            user.setId(rs.getLong(USER_ID));
            user.setEmail(rs.getString(EMAIL));
            item.setUser(user);
            items.add(item);
        }
        rs.close();
        return items;
    }

}
