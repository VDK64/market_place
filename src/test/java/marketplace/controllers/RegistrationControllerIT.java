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

import static marketplace.constants.ApplicationConstants.REGISTRATION_PAGE;
import static marketplace.constants.ApplicationConstants.WRONG_DATA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationControllerIT extends ApplicationContextStarter {
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
    public void getRegistrationPage() throws Exception {
        mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(view().name(REGISTRATION_PAGE));
    }

    @Test
    public void registerWithWrongFirstname() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("firstname", Collections.singletonList("Клара"));
            put("lastname", Collections.singletonList("Johnson"));
            put("gender", Collections.singletonList("FEMALE"));
            put("email", Collections.singletonList("asd@asd.ru"));
            put("credentials", Collections.singletonList("Passworld!1"));
            put("confirmCredentials", Collections.singletonList("Passworld!1"));
        }};

        mockMvc.perform(post("/registration")
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void registerWithWrongLastname() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("firstname", Collections.singletonList("Clara"));
            put("lastname", Collections.singletonList("Johnsonф"));
            put("gender", Collections.singletonList("FEMALE"));
            put("email", Collections.singletonList("asd@asd.ru"));
            put("credentials", Collections.singletonList("Passworld!1"));
            put("confirmCredentials", Collections.singletonList("Passworld!1"));
        }};

        mockMvc.perform(post("/registration")
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void registerWithWrongGender() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("firstname", Collections.singletonList("Clara"));
            put("lastname", Collections.singletonList("Johnson"));
            put("gender", Collections.singletonList("FEMAL"));
            put("email", Collections.singletonList("asd@asd.ru"));
            put("credentials", Collections.singletonList("Passworld!1"));
            put("confirmCredentials", Collections.singletonList("Passworld!1"));
        }};

        mockMvc.perform(post("/registration")
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void registerWithWrongEmail() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("firstname", Collections.singletonList("Clara"));
            put("lastname", Collections.singletonList("Johnson"));
            put("gender", Collections.singletonList("FEMALE"));
            put("email", Collections.singletonList("asdasdasd"));
            put("credentials", Collections.singletonList("Passworld!1"));
            put("confirmCredentials", Collections.singletonList("Passworld!1"));
        }};

        mockMvc.perform(post("/registration")
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void registerWithWrongPassword() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("firstname", Collections.singletonList("Clara"));
            put("lastname", Collections.singletonList("Johnson"));
            put("gender", Collections.singletonList("FEMALE"));
            put("email", Collections.singletonList("asd@asd.ru"));
            put("credentials", Collections.singletonList("Pas"));
            put("confirmCredentials", Collections.singletonList("Pas"));
        }};

        mockMvc.perform(post("/registration")
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(WRONG_DATA));
    }

    @Test
    public void register() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {{
            put("firstname", Collections.singletonList("Clara"));
            put("lastname", Collections.singletonList("Johnson"));
            put("gender", Collections.singletonList("FEMALE"));
            put("email", Collections.singletonList("asd@asd.ru"));
            put("credentials", Collections.singletonList("Passworld!1"));
            put("confirmCredentials", Collections.singletonList("Passworld!1"));
        }};

        mockMvc.perform(post("/registration")
                .params(params)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/login?success=1"));
    }

}