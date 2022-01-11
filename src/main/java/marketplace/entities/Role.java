package marketplace.entities;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum which represents type of role. Used in spring security.
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

}
