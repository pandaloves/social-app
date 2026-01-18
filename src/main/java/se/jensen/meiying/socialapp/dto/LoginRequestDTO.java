package se.jensen.meiying.socialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Data Transfer Object used for receiving user login credentials.
 * <p>
 * This DTO is sent from the client when a user attempts to authenticate.
 * The password field is marked as write-only to prevent it from being
 * included in API responses.
 */
public class LoginRequestDTO {

    /**
     * Username provided by the user for authentication.
     */
    private String username;

    /**
     * Password provided by the user.
     * This field is write-only and will not be returned in responses.
     */
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    /**
     * Returns the username.
     *
     * @return username used for login
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username username used for login
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password.
     *
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
