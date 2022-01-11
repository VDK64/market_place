package marketplace.controllers;

import marketplace.ApplicationContextStarter;
import marketplace.entities.User;
import marketplace.exception.UserErrorMessageConstants;
import marketplace.exception.UserException;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

import static marketplace.constants.ApplicationConstants.SHOW_ITEMS_PAGE;
import static marketplace.constants.ApplicationConstants.WRONG_DATA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
public class BidControllerIT extends ApplicationContextStarter {
    private MockMvc mockMvc;
    private User user;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    @Qualifier("UserDaoHibernateImpl")
    private UserDao userDao;

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
    public void makeBidWithWrongFloatValueAmount() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("amount", Collections.singletonList("211,16"));
            put("itemId", Collections.singletonList("2"));
        }};

        mockMvc.perform(post("/bid")
                .with(user(user))
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void makeWithWrongLongValueItemId() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("amount", Collections.singletonList("211.16"));
            put("itemId", Collections.singletonList("2.5"));
        }};

        mockMvc.perform(post("/bid")
                .with(user(user))
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void makeBidWithoutLogin() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("amount", Collections.singletonList("356.58"));
            put("itemId", Collections.singletonList("3"));
        }};

        mockMvc.perform(post("/bid")
                .params(params))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void makeBidWithoutCsrf() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("amount", Collections.singletonList("356.58"));
            put("itemId", Collections.singletonList("3"));
        }};

        mockMvc.perform(post("/bid")
                .with(user(user))
                .params(params))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void makeBidWithWrongBidIncrement() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("amount", Collections.singletonList("356.50"));
            put("itemId", Collections.singletonList("3"));
        }};

        mockMvc.perform(post("/bid")
                .with(user(user))
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void makeBidForClosedItem() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("amount", Collections.singletonList("211.16"));
            put("itemId", Collections.singletonList("2"));
        }};

        mockMvc.perform(post("/bid")
                .with(user(user))
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void makeBid() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("amount", Collections.singletonList("356.58"));
            put("itemId", Collections.singletonList("3"));
        }};

        mockMvc.perform(post("/bid")
                .with(user(user))
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name(SHOW_ITEMS_PAGE));
    }

}