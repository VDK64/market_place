package marketplace.persistence.hibernateimpl;

import marketplace.dto.persistence.ItemPersistenceDto;
import marketplace.entities.Item;
import marketplace.persistence.ItemDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static marketplace.queries.ItemQueriesConstants.*;

/**
 * Data access object layer to implements working with database.
 */
@Component("ItemDaoHibernateImpl")
public class ItemDaoHibernateImpl implements ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save item in database.
     *
     * @param itemDto which need to save.
     */
    @Override
    @Transactional
    public void saveItem(ItemPersistenceDto itemDto) {
        final Item item = assembleEntity(itemDto);
        entityManager.merge(item);
    }

    /**
     * Get all items from a database.
     *
     * @return list of items.
     */
    @Override
    public List<Item> getAllItems() {
        return entityManager
                .createQuery(SELECT_ALL_ITEMS, Item.class)
                .getResultList();
    }

    /**
     * Method for receiving items of current user.
     *
     * @param email of a user (principal).
     * @return list of items from a database.
     */
    @Override
    public List<Item> getCurrentUserItems(String email) {
        return entityManager.createQuery(SELECT_CURRENT_USER_ITEMS, Item.class)
                .setParameter(1, email)
                .getResultList();
    }

    /**
     * Find item for it id in a database.
     *
     * @param itemId is the id of item.
     * @return item, wrapped in <code>Optional</code> class.
     */
    @Override
    public Optional<Item> findItemById(Long itemId) {
        final Item item = entityManager.find(Item.class, itemId);
        return Optional.of(item);
    }

    /**
     * Returns proper items by find request.
     *
     * @param find is a request to search in descriptions an titles of items.
     * @return list of found items.
     */
    @Override
    public List<Item> getProperItems(String find) {
        return entityManager
                .createQuery(
                        SELECT_PROPER_ITEM, Item.class)
                .setParameter(1, find)
                .setParameter(2, find)
                .getResultList();
    }

    /**
     * Get image of a concrete item from database.
     *
     * @param id of item which image need to get.
     * @return array of bytes.
     */
    @Override
    public byte[] getItemImageById(long id) {
        final Item item = entityManager.find(Item.class, id);
        return item.getImage();
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