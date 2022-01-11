package marketplace.controllers;

import marketplace.ApplicationContextStarter;
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

import java.util.Collections;

import static marketplace.constants.ApplicationConstants.LOGIN_PAGE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerIT extends ApplicationContextStarter {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    @Qualifier("UserDaoHibernateImpl")
    private UserDao userDao;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(view().name(LOGIN_PAGE));
    }

    @Test
    public void login() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("username", Collections.singletonList("petro@mail.ru"));
            put("password", Collections.singletonList(("myPass")));
        }};

        mockMvc.perform(post("/login")
                .with(csrf())
                .params(params))
                .andDo(print())
                .andExpect(forwardedUrl("/"));
    }

    @Test
    public void loginWithoutCsrf() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("username", Collections.singletonList("petro@mail.ru"));
            put("password", Collections.singletonList(("myPass")));
        }};

        mockMvc.perform(post("/login")
                .params(params))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void loginError() throws Exception {
        mockMvc.perform(post("/login")
                .with(csrf()))
                .andDo(print())
                .andExpect(forwardedUrl("/login?error=1"));
    }

}