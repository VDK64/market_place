package marketplace.controllers;

import marketplace.ApplicationContextStarter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static marketplace.constants.ApplicationConstants.SHOW_ITEMS_PAGE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
public class ItemsControllerIT extends ApplicationContextStarter {
    private MockMvc mockMvc;
    private static final String USER = "petro@mail.ru";

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getItems() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
                .andExpect(view().name(SHOW_ITEMS_PAGE));
    }

    @Test
    public void logout() throws Exception {
        mockMvc.perform(post("/")
                .with(user(USER))
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name(SHOW_ITEMS_PAGE));
    }

}