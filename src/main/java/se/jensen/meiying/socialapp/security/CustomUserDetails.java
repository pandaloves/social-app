package se.jensen.meiying.socialapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.jensen.meiying.socialapp.model.User;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link UserDetails} for Spring Security,
 * wrapping the {@link User} entity from the application.
 * <p>
 * This class provides Spring Security with necessary user information
 * such as username, password, authorities, and account status.
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * Creates a new {@link CustomUserDetails} wrapping the given {@link User}.
     *
     * @param user The user entity to wrap.
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Returns the underlying {@link User} entity.
     *
     * @return The domain user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the authorities granted to the user.
     * <p>
     * The authority is generated from the user's role by prefixing with "ROLE_".
     *
     * @return A collection of {@link GrantedAuthority}.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return The user's username.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true, as accounts never expire in this implementation.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return true, as accounts are never locked in this implementation.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return true, as credentials never expire in this implementation.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true, as users are always enabled in this implementation.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Returns the underlying domain {@link User} entity.
     * Alias for {@link #getUser()}.
     *
     * @return The wrapped User entity.
     */
    public User getDomainUser() {
        return user;
    }
}
