package marketplace.services.impl;

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
import marketplace.services.ItemService;
import marketplace.types.Gender;
import marketplace.validation.validatiors.CommonValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BidServiceImplTest {

    @Mock
    private BidDao bidDao;

    @Mock
    private ItemDao itemDao;

    @Mock
    private ItemService itemService;

    @Mock
    private CommonValidator validator;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private BidServiceImpl test;

    @Test(expected = BidException.class)
    public void makeBidWithErrors() throws SQLException, IOException {
        when(bindingResult.hasErrors()).thenReturn(true);
        test.makeBid(new User(), new BidDto(), bindingResult);
    }

    @Test(expected = ItemException.class)
    public void makeBidForAbsentItem()
            throws SQLException, IOException {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(itemDao.findItemById(anyLong()))
                .thenThrow(new ItemException(ItemErrorMessageConstants.WRONG_ITEM));
        final BidDto bidDto = new BidDto();
        bidDto.setAmount("500");
        bidDto.setItemId("500");
        final User user = new User();
        user.setGender(Gender.FEMALE);
        test.makeBid(user, bidDto, bindingResult);
    }

    @Test(expected = BidException.class)
    public void makeBidWithWrongBidValidationDto()
            throws SQLException, IOException {
        final BidDto bidDto = new BidDto();
        bidDto.setAmount("500");
        bidDto.setItemId("500");
        final User user = new User();
        user.setGender(Gender.FEMALE);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(itemDao.findItemById(500L)).thenReturn(Optional.of(new Item()));
        doThrow(new BidException("testMessage"))
                .when(validator)
                .validateBeforeBid(new BidValidationDto(500F, user, new Item()));
        test.makeBid(user, bidDto, bindingResult);
    }

    @Test
    public void makeBid() throws SQLException, IOException {
        final BidDto bidDto = new BidDto();
        bidDto.setAmount("500");
        bidDto.setItemId("500");
        final User user = new User();
        user.setGender(Gender.FEMALE);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(itemDao.findItemById(500L)).thenReturn(Optional.of(new Item()));
        doNothing().when(validator)
                .validateBeforeBid(any());
        doNothing().when(bidDao)
                .doBid(any());
        doNothing().when(itemDao)
                .saveItem(any());
        when(itemService.assembleItemDto(any())).thenReturn(new ItemPersistenceDto());
        test.makeBid(user, bidDto, bindingResult);
    }


}