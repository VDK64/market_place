package marketplace.services.impl;

import marketplace.dto.web.ItemDto;
import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.exception.ItemException;
import marketplace.persistence.ItemDao;
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
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceImplTest {
    private static final String DEFAULT_IMAGE_PATH = "/static/logos/defaultImage.png";
    private static final String PERCENT = "%";

    @Mock
    private ItemDao itemDao;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private CommonValidator commonValidator;

    @InjectMocks
    private ItemServiceImpl test;

    @Test
    public void getAllItemsTest() throws SQLException {
        when(itemDao.getAllItems()).thenReturn(new ArrayList<>());
        test.getAllItems();
    }

    @Test
    public void getCurrentUserItemsTest() throws SQLException, IOException {
        when(itemDao.getCurrentUserItems(anyString())).thenReturn(new ArrayList<>());
        test.getCurrentUserItems(anyString());
    }

    @Test
    public void getItemNullTest() throws SQLException, IOException {
        final Item item = test.getItem(null);
        assertNull(item);
    }

    @Test
    public void getItemEmptyTest() throws SQLException, IOException {
        when(itemDao.findItemById(anyLong())).thenReturn(Optional.empty());
        final Item item = test.getItem("1");
        assertNull(item);
    }

    @Test
    public void getItemTest() throws SQLException, IOException {
        when(itemDao.findItemById(anyLong())).thenReturn(Optional.of(new Item()));
        final Item item = test.getItem("1");
        assertNotNull(item);
    }

    @Test(expected = ItemException.class)
    public void editItemWithErrors() throws SQLException, IOException {
        when(bindingResult.hasErrors()).thenReturn(true);
        test.saveItem(new ItemDto(), bindingResult, null, new User());
    }

    @Test
    public void editItemTest() throws SQLException, IOException {
        final ItemDto itemDto = new ItemDto();
        itemDto.setItemId("1");
        itemDto.setTitle("Title");
        itemDto.setDescription("Description");
        itemDto.setStartPrice("100");
        itemDto.setBidIncrement("50");
        itemDto.setStopDate("2025-05-05");
        itemDto.setStopTime("12:00");
        final User user = new User();
        user.setId(17L);
        user.setGender(Gender.MALE);
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(itemDao).saveItem(any());
        when(itemDao.findItemById(anyLong())).thenReturn(Optional.of(new Item()));
        test.saveItem(itemDto, bindingResult, null, user);
    }

    @Test
    public void saveItemTest() throws SQLException, IOException {
        final ItemDto itemDto = new ItemDto();
        itemDto.setItemId(null);
        itemDto.setTitle("Title");
        itemDto.setDescription("Description");
        itemDto.setStartPrice("100");
        itemDto.setBidIncrement("50");
        itemDto.setStopDate("2025-05-05");
        itemDto.setStopTime("12:00");
        final User user = new User();
        user.setId(17L);
        user.setGender(Gender.MALE);
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(itemDao).saveItem(any());
        test.saveItem(itemDto, bindingResult, null, user);
    }

    @Test
    public void getDateTest() {
        final Item item = new Item();
        item.setStopDate("2025-05-05 11:20");
        final String date = test.getDate(item);
        assertEquals("2025-05-05", date);
    }

    @Test
    public void getTimeTest() {
        final Item item = new Item();
        item.setStopDate("2025-05-05 11:20");
        final String time = test.getTime(item);
        assertEquals("11:20", time);
    }

    @Test
    public void getProperItemsTest() throws SQLException {
        String find = "find";
        when(itemDao.getProperItems(anyString())).thenReturn(new ArrayList<>());
        test.getProperItems(anyString());
    }

}