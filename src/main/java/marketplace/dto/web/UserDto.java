package marketplace.dto.web;

import lombok.Data;
import marketplace.validation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static marketplace.exception.UserErrorMessageConstants.*;

/**
 * This is a User class for data transfer.
 * Represents data which server obtains from registration form.
 */
@Data
@PasswordMatcher(field = "credentials", fieldMatch = "confirmCredentials")
public class UserDto {
    @Size(min = 2, max = 50)
    @NotBlank(message = EMPTY_FIRST_NAME)
    @Latin
    private String firstname;

    @Size(min = 2, max = 50)
    @NotBlank(message = EMPTY_LAST_NAME)
    @Latin
    private String lastname;

    @NotNull
    @GenderConstraint
    private String gender;

    @NotNull
    @Password
    private String credentials;

    @NotBlank(message = EMPTY_EMAIL)
    @Email(message = INVALID_EMAIL)
    @IsExist
    private String email;

    @NotNull
    private String confirmCredentials;

}
