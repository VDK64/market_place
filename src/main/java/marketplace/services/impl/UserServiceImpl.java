package marketplace.services.impl;

import marketplace.dto.persistence.UserPersistenceDto;
import marketplace.dto.web.ReturnUserDto;
import marketplace.dto.web.UserDto;
import marketplace.entities.Role;
import marketplace.entities.User;
import marketplace.exception.UserAlreadyExistsException;
import marketplace.exception.UserErrorMessageConstants;
import marketplace.exception.UserException;
import marketplace.persistence.UserDao;
import marketplace.services.UserService;
import marketplace.types.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

import static marketplace.exception.UserErrorMessageConstants.ALREADY_EXISTS;

@Component
public class UserServiceImpl implements UserService {
    private static final String CHECKED = "checked";
    private static final String EXIST_CODE = "IsExist";

    @Autowired
    @Qualifier("UserDaoHibernateImpl")
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto, BindingResult result,
                         ModelAndView modelAndView) throws SQLException, IOException {
        checkOnErrors(userDto, result, modelAndView);
        final UserPersistenceDto userPersistenceDto = assembleUserPersistenceDto(userDto);
        userDao.saveUser(userPersistenceDto);
    }

    @Override
    public User getUser(String email) throws IOException, SQLException {
        final Optional<User> userByEmail = userDao.findByEmail(email);
        return userByEmail.orElseThrow(
                () -> new UserException(UserErrorMessageConstants.NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userByEmail = Optional.empty();
        try {
            userByEmail = userDao.findByEmail(email);
        } catch (SQLException | IOException exception) {
            exception.printStackTrace();
        }
        return userByEmail.orElseThrow(
                () -> new UserException(UserErrorMessageConstants.NOT_FOUND));
    }

    private UserPersistenceDto assembleUserPersistenceDto(UserDto userDto) {
        final UserPersistenceDto userPersistenceDto = new UserPersistenceDto();
        userPersistenceDto.setFirstName(userDto.getFirstname());
        userPersistenceDto.setLastName(userDto.getLastname());
        userPersistenceDto.setGender(Gender.valueOf(userDto.getGender()));
        userPersistenceDto.setEmail(userDto.getEmail());
        userPersistenceDto.setPassword(passwordEncoder.encode(userDto.getCredentials()));
        userPersistenceDto.setAuthorities(new HashSet<>(Collections.singletonList(Role.USER)));
        return userPersistenceDto;
    }

    private void checkOnErrors(UserDto userDto, BindingResult result, ModelAndView modelAndView) {
        if (result.hasErrors()) {
            if (result.getFieldErrors().size() == 1
                    && Objects.equals(result.getFieldErrors().get(0).getCode(), EXIST_CODE)) {
                final ReturnUserDto returnUserDto = assembleReturnUserDto(userDto);
                throw new UserAlreadyExistsException(
                        ALREADY_EXISTS, returnUserDto,
                        modelAndView,
                        result.getFieldErrors().get(0));
            } else {
                throw new UserException(modelAndView, result.getFieldErrors());
            }
        }
    }

    private ReturnUserDto assembleReturnUserDto(UserDto userDto) {
        final ReturnUserDto returnUserDto = new ReturnUserDto();
        returnUserDto.setFirstName(userDto.getFirstname());
        returnUserDto.setLastName(userDto.getLastname());
        setGenderCheckToDto(returnUserDto, userDto.getGender());
        returnUserDto.setEmail(userDto.getEmail());
        return returnUserDto;
    }

    private void setGenderCheckToDto(ReturnUserDto returnUserDTO, String gender) {
        if (Gender.MALE.equals(Gender.valueOf(gender))) {
            returnUserDTO.setMaleGenderChecked(CHECKED);
        } else {
            returnUserDTO.setFemaleGenderChecked(CHECKED);
        }
    }

}
