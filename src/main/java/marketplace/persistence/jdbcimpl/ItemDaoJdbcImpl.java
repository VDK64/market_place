package marketplace.persistence.jdbcimpl;

import marketplace.dto.persistence.ItemPersistenceDto;
import marketplace.entities.Item;
import marketplace.persistence.ItemDao;
import marketplace.persistence.mappers.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static marketplace.constants.ApplicationConstants.NOBODY;
import static marketplace.queries.ItemQueriesConstants.*;

/**
 * Data access object layer to implements working with database.
 */
@Component("ItemDaoJdbcImpl")
public class ItemDaoJdbcImpl implements ItemDao {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private DataSource dataSource;

    /**
     * Save item in database.
     *
     * @param itemDto which need to save.
     * @throws SQLException will thrown when sql exception occurred.
     */
    @Override
    public void saveItem(ItemPersistenceDto itemDto) throws SQLException {
        final Item item = assembleEntity(itemDto);
        if (item.getId() == 0L) {
            doSave(item);
        } else {
            doEditItem(item);
        }
    }

    private void doSave(Item item) throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     .prepareStatement(INSERT_INTO_ITEM)) {
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setString(3, item.getSeller());
            preparedStatement.setFloat(4, item.getStartPrice());
            preparedStatement.setFloat(5, item.getBidInc());
            preparedStatement.setFloat(6, 0L);
            preparedStatement.setString(7, NOBODY);
            preparedStatement.setString(8, item.getStopDate());
            preparedStatement.setLong(9, item.getUser().getId());
            preparedStatement.setBytes(10, item.getImage());
            preparedStatement.execute();
        }
    }

    private void doEditItem(Item item)
            throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     .prepareStatement(UPDATE_ITEM)) {
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setFloat(3, item.getBestOffer());
            preparedStatement.setString(4, item.getBidder());
            preparedStatement.setLong(5, item.getId());
            preparedStatement.execute();
        }
    }

    /**
     * Get all items from a database.
     *
     * @return list of items.
     * @throws SQLException will thrown when sql exception occurred.
     */
    @Override
    public List<Item> getAllItems() throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SELECT_FROM_ITEM);
            return itemMapper.mapItems(rs);
        }
    }

    /**
     * Method for receiving items of current user.
     *
     * @param email of a user (principal).
     * @return list of items from a database.
     * @throws SQLException will thrown when sql exception occurred.
     */
    @Override
    public List<Item> getCurrentUserItems(String email) throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_FROM_ITEM_BY_USER_ID)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            return itemMapper.mapItems(rs);
        }
    }

    /**
     * Find item for it id in a database.
     *
     * @param itemId is the id of item.
     * @return item, wrapped in <code>Optional</code> class.
     * @throws SQLException will thrown when sql exception occurred.
     */
    @Override
    public Optional<Item> findItemById(Long itemId) throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_FROM_ITEM_BY_ID)) {
            preparedStatement.setLong(1, itemId);
            ResultSet rs = preparedStatement.executeQuery();
            final Item item = itemMapper.mapItem(rs);
            if (item.getId() == 0) {
                return Optional.empty();
            }
            return Optional.of(item);
        }
    }

    @Override
    public List<Item> getProperItems(String find) throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_PROPER_ITEMS)) {
            preparedStatement.setString(1, find);
            preparedStatement.setString(2, find);
            final ResultSet resultSet = preparedStatement.executeQuery();
            return itemMapper.mapItems(resultSet);
        }
    }

    @Override
    public byte[] getItemImageById(long id) throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_ITEM_IMAGE)) {
            preparedStatement.setLong(1, id);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null) {
                    resultSet.next();
                    return resultSet.getBytes(1);
                }
            }
        }
        return null;
    }

    private Item assembleEntity(ItemPersistenceDto itemDto) {
        final Item item = new Item();
        item.setId(itemDto.getId());
        item.setTitle(itemDto.getTitle());
        item.setDescription(itemDto.getDescription());
        item.setStartPrice(itemDto.getStartPrice());
        item.setBidInc(itemDto.getBidInc());
        item.setBestOffer(itemDto.getBestOffer());
        item.setBidder(itemDto.getBidder());
        item.setSeller(itemDto.getSeller());
        item.setStopDate(itemDto.getStopDate());
        item.setUser(itemDto.getUser());
        item.setImage(itemDto.getImage());
        return item;
    }

}