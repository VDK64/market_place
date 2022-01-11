package marketplace.persistence.hibernateimpl;

import marketplace.dto.persistence.BidPersistenceDto;
import marketplace.entities.Bid;
import marketplace.persistence.BidDao;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Data access object layer to implements working with database.
 */
@Component("BidDaoHibernateImpl")
public class BidDaoHibernateImpl implements BidDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Do bid. Simple...
     *
     * @param bidDto which need to do.
     */
    @Override
    public void doBid(BidPersistenceDto bidDto) {
        final Bid bid = assembleEntity(bidDto);
        entityManager.persist(bid);
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
