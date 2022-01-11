package marketplace.services.impl;

import marketplace.dto.persistence.BidPersistenceDto;
import marketplace.dto.persistence.ItemPersistenceDto;
import marketplace.dto.web.BidDto;
import marketplace.dto.web.BidValidationDto;
import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.exception.BidException;
import marketplace.exception.ItemErrorMessageConstants;
import marketplace.exception.ItemException;
import marketplace.persistence.BidDao;
import marketplace.persistence.ItemDao;
import marketplace.services.BidService;
import marketplace.services.ItemService;
import marketplace.validation.validatiors.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static marketplace.constants.ApplicationConstants.DATE_FORMAT;

/**
 * Implementation of {@link BidService}.
 */
@Component
public class BidServiceImpl implements BidService {
    private final SimpleDateFormat dateFormat;

    @Autowired
    @Qualifier("BidDaoHibernateImpl")
    private BidDao bidDao;

    @Autowired
    @Qualifier("ItemDaoHibernateImpl")
    private ItemDao itemDao;

    @Autowired
    private CommonValidator validator;

    @Autowired
    private ItemService itemService;

    public BidServiceImpl() {
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    /**
     * Make bid and persist in database.
     *
     * @param user   which doing bid.
     * @param bidDto is data of user request for bid.
     */
    @Override
    @Transactional
    public void makeBid(User user, BidDto bidDto, BindingResult result)
            throws IOException, SQLException {
        checkOnErrors(result);
        final long itemId = Long.parseLong(bidDto.getItemId());
        final float amount = new BigDecimal(bidDto.getAmount())
                .setScale(2, RoundingMode.CEILING).floatValue();
        final String nowDateString = dateFormat
                .format(new Date(System.currentTimeMillis() + 10000));
        final String bidder = createBidderWithGenderPrefix(user);
        final Item item = itemDao.findItemById(itemId).orElseThrow(
                () -> new ItemException(ItemErrorMessageConstants.WRONG_ITEM));
        validator.validateBeforeBid(new BidValidationDto(amount, user, item));
        final BidPersistenceDto bidPersistenceDto
                = assembleBidDto(nowDateString, amount, item, user);
        bidDao.doBid(bidPersistenceDto);
        changeAmountAndBidder(item, amount, bidder);
        final ItemPersistenceDto itemDto = itemService.assembleItemDto(item);
        itemDao.saveItem(itemDto);
    }

    private void changeAmountAndBidder(Item item, float amount, String bidder) {
        item.setBidder(bidder);
        item.setBestOffer(amount);
    }

    /**
     * Assembles item data transfer object to persistence layer.
     * @param item from which dto will assemble.
     * @return data transfer object.
     */
    @Override
    public BidPersistenceDto assembleBidDto(String nowDateString, float amount, Item item, User user) {
        final BidPersistenceDto bidDto = new BidPersistenceDto();
        bidDto.setAmount(amount);
        bidDto.setTimeStamp(nowDateString);
        bidDto.setItem(item);
        bidDto.setUser(user);
        return bidDto;
    }

    private void checkOnErrors(BindingResult result) {
        if (result.hasErrors()) {
            throw new BidException(result.getFieldErrors());
        }
    }

    private String createBidderWithGenderPrefix(User user) {
        final String prefix = user.getGender().getPrefix();
        return prefix + user.getLastName();
    }

}