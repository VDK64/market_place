package marketplace.controllers;

import marketplace.ApplicationContextStarter;
import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.exception.ItemErrorMessageConstants;
import marketplace.exception.ItemException;
import marketplace.exception.UserErrorMessageConstants;
import marketplace.exception.UserException;
import marketplace.persistence.ItemDao;
import marketplace.persistence.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static marketplace.constants.ApplicationConstants.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class EditItemControllerIT extends ApplicationContextStarter {
    private MockMvc mockMvc;
    private User user;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    @Qualifier("UserDaoHibernateImpl")
    private UserDao userDao;

    @Autowired
    @Qualifier("ItemDaoHibernateImpl")
    private ItemDao itemDao;

    @Before
    public void setup() throws IOException, SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
        user = userDao.findByEmail("petro@mail.ru")
                .orElseThrow(() -> new UserException(UserErrorMessageConstants.NOT_FOUND));
    }

    @Test
    public void getItemWithoutLogin() throws Exception {
        mockMvc.perform(get("/item")
                .param(ITEM_ID, "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void getItem() throws Exception {
        final ModelAndView resultModel = mockMvc.perform(get("/item")
                .with(user(user))
                .param(ITEM_ID, "1"))
                .andDo(print())
                .andExpect(view().name(EDIT_ITEM_PAGE))
                .andReturn()
                .getModelAndView();

        final Item itemFromDatabase = itemDao.findItemById(1L).orElseThrow(
                () -> new ItemException(ItemErrorMessageConstants.NOT_FOUND));
        final Map<String, Object> model = Objects.requireNonNull(resultModel).getModel();
        final Item item = (Item) model.get(ITEM);
        assertEquals(item, itemFromDatabase);
        assertEquals(DISABLED, model.get(DISABLED));
        assertEquals("11:15", model.get(STOP_TIME_CAMEL_CASE));
        assertEquals("2025-09-17", model.get(STOP_DATE_CAMEL_CASE));
    }

    @Test
    public void createItemWithWrongStartPrice() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("title", Collections.singletonList("My Item."));
            put("description", Collections.singletonList("This is my description"));
            put("startPrice", Collections.singletonList("300,300"));
            put("bidIncrement", Collections.singletonList("200.200"));
            put("stopTime", Collections.singletonList("22:50"));
            put("stopDate", Collections.singletonList("2020-09-26"));
        }};

        mockMvc.perform(post("/item")
                .with(user(user))
                .with(csrf())
                .params(params))
                .andDo(print())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void createItemWithWrongBidIncrement() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("title", Collections.singletonList("My Item."));
            put("description", Collections.singletonList("This is my description"));
            put("startPrice", Collections.singletonList("300.300"));
            put("bidIncrement", Collections.singletonList("200,200"));
            put("stopTime", Collections.singletonList("22:50"));
            put("stopDate", Collections.singletonList("2020-09-26"));
        }};

        mockMvc.perform(post("/item")
                .with(user(user))
                .with(csrf())
                .params(params))
                .andDo(print())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void createItemWithWrongTime() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("title", Collections.singletonList("My Item."));
            put("description", Collections.singletonList("This is my description"));
            put("startPrice", Collections.singletonList("300.300"));
            put("bidIncrement", Collections.singletonList("200.200"));
            put("stopTime", Collections.singletonList("22:5"));
            put("stopDate", Collections.singletonList("2020-09-26"));
        }};

        mockMvc.perform(post("/item")
                .with(user(user))
                .with(csrf())
                .params(params))
                .andDo(print())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void createItem() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("title", Collections.singletonList("My Item."));
            put("description", Collections.singletonList("This is my description"));
            put("startPrice", Collections.singletonList("300.300"));
            put("bidIncrement", Collections.singletonList("200.200"));
            put("stopTime", Collections.singletonList("22:50"));
            put("stopDate", Collections.singletonList("2020-09-26"));
        }};

        mockMvc.perform(post("/item")
                .with(user(user))
                .with(csrf())
                .params(params))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }

}