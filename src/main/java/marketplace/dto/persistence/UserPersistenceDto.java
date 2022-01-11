package marketplace.dto.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace.entities.Role;
import marketplace.types.Gender;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPersistenceDto {
    private long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String password;
    private Set<Role> authorities;

}
