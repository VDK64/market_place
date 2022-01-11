package marketplace.services.impl;

import marketplace.dto.persistence.ItemPersistenceDto;
import marketplace.dto.web.ItemDto;
import marketplace.dto.web.ResponseDto;
import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.exception.ItemException;
import marketplace.exception.UserException;
import marketplace.persistence.ItemDao;
import marketplace.services.ItemService;
import marketplace.validation.validatiors.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static marketplace.constants.ApplicationConstants.NOBODY;
import static marketplace.constants.ApplicationConstants.WHITE_SPACE;
import static marketplace.exception.UserErrorMessageConstants.NOT_FOUND;

/**
 * Implementation of {@link ItemService}.
 */
@Component
public class ItemServiceImpl implements ItemService {
    private static final String DEFAULT_IMAGE_PATH = "/static/logos/defaultImage.png";
    private static final String PERCENT = "%";

    @Autowired
    @Qualifier("ItemDaoHibernateImpl")
    private ItemDao itemDao;

    @Autowired
    private CommonValidator commonValidator;

    /**
     * Returns response in JSON. Response consists of list of items.
     *
     * @return ResponseDTO is a representation of list of items.
     */
    @Override
    public ResponseDto getAllItems() throws SQLException {
        return new ResponseDto(new ArrayList<>(itemDao.getAllItems()), false);
    }

    /**
     * Getting current user items.
     *
     * @param email of user.
     * @return data transfer object which represents items data.
     * @throws SQLException would be thrown if corresponding exception occurs.
     * @throws IOException  would be thrown if corresponding exception occurs.
     */
    @Override
    public ResponseDto getCurrentUserItems(String email) throws SQLException, IOException {
        return new ResponseDto(new ArrayList<>(itemDao.getCurrentUserItems(email)), false);
    }

    /**
     * Find item by it's id.
     *
     * @param itemId is id of item which need to find.
     * @return found item.
     * @throws SQLException if occurred.
     */
    @Override
    public Item getItem(String itemId) throws SQLException, IOException {
        if (itemId == null) {
            return null;
        }
        final Optional<Item> itemById = itemDao.findItemById(Long.valueOf(itemId));
        return itemById.orElse(null);
    }

    /**
     * Saving item method.
     *
     * @param itemDto   dto with item parameters received on server from user.
     * @param result    is a storage of errors which could occur due validate item.
     * @param file      represents image sending by user.
     * @param principal who send request to edit item.
     * @throws SQLException would be thrown if exception occurs.
     * @throws IOException  would be thrown if exception occurs.
     */
    @Override
    @Transactional
    public void saveItem(ItemDto itemDto, BindingResult result, MultipartFile file, User principal)
            throws SQLException, IOException {
        checkOnErrors(result);
        Item item;
        if (itemDto.getItemId() != null) {
            final Optional<Item> itemByIdOptional = itemDao.findItemById(Long.valueOf(itemDto.getItemId()));
            final Item itemById = itemByIdOptional.orElseThrow(() -> new UserException(NOT_FOUND));
            item = createItemForUpdateFromItem(itemById, itemDto, file, principal);
        } else {
            item = createItemForUpdateFromItem(new Item(), itemDto, file, principal);
        }
        final ItemPersistenceDto itemPersistenceDto = assembleItemDto(item);
        itemDao.saveItem(itemPersistenceDto);
    }

    /**
     * Get representing in a string date from item.
     *
     * @param item which contains proper date.
     * @return date in string.
     */
    @Override
    public String getDate(Item item) {
        final String[] splitDate = splitDate(item);
        return splitDate[0];
    }

    /**
     * Get representing in a string time from item.
     *
     * @param item which contains proper date.
     * @return time in string.
     */
    @Override
    public String getTime(Item item) {
        final String[] splitDate = splitDate(item);
        return splitDate[1];
    }

    /**
     * Get items by matched name or description.
     *
     * @param find is a string value which need to match.
     * @return found items in data transfer object.
     * @throws SQLException would be thrown if exception occurs.
     */
    @Override
    public ResponseDto getProperItems(String find) throws SQLException {
        final String findWithPrefix = makeSuffixAndPrefix(find);
        return new ResponseDto(new ArrayList<>(itemDao.getProperItems(findWithPrefix)), false);
    }

    /**
     * Assembles item data transfer object to persistence layer.
     * @param item from which dto will assemble.
     * @return data transfer object.
     */
    @Override
    public ItemPersistenceDto assembleItemDto(Item item) {
        final ItemPersistenceDto itemDto = new ItemPersistenceDto();
        itemDto.setId(item.getId());
        itemDto.setTitle(item.getTitle());
        itemDto.setDescription(item.getDescription());
        itemDto.setStartPrice(item.getStartPrice());
        itemDto.setBidInc(item.getBidInc());
        itemDto.setBestOffer(item.getBestOffer());
        itemDto.setBidder(item.getBidder());
        itemDto.setSeller(item.getSeller());
        itemDto.setStopDate(item.getStopDate());
        itemDto.setUser(item.getUser());
        itemDto.setImage(item.getImage());
        return itemDto;
    }

    private void checkOnErrors(BindingResult result) {
        if (result.hasErrors()) {
            throw new ItemException(result.getFieldErrors());
        }
    }

    private String[] splitDate(Item item) {
        final String stopDate = item.getStopDate();
        return stopDate.split(WHITE_SPACE);
    }

    private Item createItemForUpdateFromItem(
            Item item, ItemDto itemDto, MultipartFile file, User principal)
            throws IOException {
        item.setTitle(itemDto.getTitle());
        item.setUser(principal);
        item.setDescription(itemDto.getDescription());
        setImage(file, item);
        setBidder(item);
        setStartPriceAndBidIncrement(itemDto, item);
        createDate(itemDto, item);
        setSellerWithPrefix(item, principal);
        return item;
    }

    private void setBidder(Item item) {
        if (item.getBidder() == null) {
            item.setBidder(NOBODY);
        }
    }

    private void setImage(MultipartFile file, Item item) throws IOException {
        if (item.getId() == 0L) {
            commonValidator.validateImage(file);
            if (file != null && !file.isEmpty()) {
                item.setImage(file.getBytes());
            } else {
                final InputStream resourceAsStream = this.getClass()
                        .getResourceAsStream(DEFAULT_IMAGE_PATH);
                final byte[] bytes = new byte[resourceAsStream.available()];
                resourceAsStream.read(bytes);
                item.setImage(bytes);
            }
        }
    }

    private void setSellerWithPrefix(Item item, User principal) {
        final String prefix = principal.getGender().getPrefix();
        item.setSeller(prefix + principal.getLastName());
    }

    private void setStartPriceAndBidIncrement(ItemDto itemDto,
                                              Item item) {
        if (itemDto.getStartPrice() != null && itemDto.getBidIncrement() != null && item.getId() == 0L) {
            final BigDecimal startPrice = new BigDecimal(itemDto.getStartPrice())
                    .setScale(2, RoundingMode.CEILING);
            item.setStartPrice(startPrice.floatValue());
            final BigDecimal bidIncrement = new BigDecimal(itemDto.getBidIncrement())
                    .setScale(2, RoundingMode.CEILING);
            item.setBidInc(bidIncrement.floatValue());
        }
    }

    private void createDate(ItemDto itemDto, Item item) {
        if (itemDto.getStopDate() != null) {
            final String stopDate = itemDto.getStopDate();
            final String stopTime = itemDto.getStopTime();
            final String finalDate = stopDate + " " + stopTime;
            item.setStopDate(finalDate);
        }
    }

    private String makeSuffixAndPrefix(String find) {
        return PERCENT + find + PERCENT;
    }

}
