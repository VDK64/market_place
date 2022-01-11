package marketplace.services.impl;

import marketplace.dto.web.UserDto;
import marketplace.entities.User;
import marketplace.exception.UserAlreadyExistsException;
import marketplace.exception.UserException;
import marketplace.persistence.UserDao;
import marketplace.types.Gender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String EXIST_CODE = "IsExist";

    @Mock
    private UserDao userDao;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserServiceImpl test;

    @Test(expected = UserAlreadyExistsException.class)
    public void saveUserWithUserAlreadyExistsExceptionTest() throws IOException, SQLException {
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(new ArrayList<>(Collections
                .singleton(new FieldError("name", "field", EXIST_CODE,
                        false, new String[]{EXIST_CODE},
                        null, null))));
        final UserDto userDto = createUSerDto();
        test.saveUser(userDto, bindingResult, new ModelAndView());
    }

    @Test(expected = UserException.class)
    public void saveUserWithUserExceptionTest() throws IOException, SQLException {
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(new ArrayList<>());
        final UserDto userDto = createUSerDto();
        test.saveUser(userDto, bindingResult, new ModelAndView());
    }

    @Test
    public void saveUserTest() throws IOException, SQLException {
        final UserDto userDto = createUSerDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("pass");
        doNothing().when(userDao).saveUser(any());
        test.saveUser(userDto, bindingResult, new ModelAndView());
    }

    @Test(expected = UserException.class)
    public void getUserUserExceptionTest() throws IOException, SQLException {
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        test.getUser(anyString());
    }

    @Test
    public void getUserTest() throws IOException, SQLException {
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        final User user = test.getUser(anyString());
        assertNotNull(user);
    }

    @Test(expected = UserException.class)
    public void loadByUsernameWithErrorTest() throws IOException, SQLException {
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        test.loadUserByUsername(anyString());
    }

    @Test
    public void loadByUsernameTest() throws IOException, SQLException {
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        final UserDetails user = test.loadUserByUsername(anyString());
        assertNotNull(user);
    }

    private UserDto createUSerDto() {
        final UserDto userDto = new UserDto();
        userDto.setFirstname("Firstname");
        userDto.setLastname("Lastname");
        userDto.setGender(Gender.MALE.toString());
        userDto.setEmail("mail@mail.ru");
        userDto.setCredentials("password");
        userDto.setConfirmCredentials("password");
        return userDto;
    }

}