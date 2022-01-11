package marketplace.entities;

import lombok.Data;
import marketplace.types.Gender;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity for database store. Represents User.
 */
@Data
@Entity
@Table(name = "Usr", uniqueConstraints = @UniqueConstraint(
        name = "usr_email_key",
        columnNames = {"email"}
), indexes = @Index(
        name = "usr_email_key",
        columnList = "email",
        unique = true
))
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    @CollectionTable(name = "Role_User", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities;

    public User() {
        this.authorities = new HashSet<>();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}




