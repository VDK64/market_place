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
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.sql.SQLException;

import static marketplace.constants.ApplicationConstants.MY_ITEMS_PAGE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserItemsControllerIT extends ApplicationContextStarter {
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
    public void getMyItemsWithoutLogin() throws Exception {
        mockMvc.perform(get("/myItems"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void getMyItems() throws Exception {
        mockMvc.perform(get("/myItems")
                .with(user(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(MY_ITEMS_PAGE));
    }

}