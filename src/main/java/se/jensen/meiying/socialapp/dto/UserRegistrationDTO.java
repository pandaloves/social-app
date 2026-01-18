package se.jensen.meiying.socialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Data Transfer Object (DTO) for registering a new user.
 * <p>
 * Contains user information required during registration, including
 * username, email, password, display name, and optional bio.
 */
public class UserRegistrationDTO {

    /**
     * Username chosen by the user
     */
    private String username;

    /**
     * Email address of the user
     */
    private String email;

    /**
     * Password of the user (write-only for security)
     */
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    /**
     * Display name of the user
     */
    private String displayName;

    /**
     * Short biography or description for the user
     */
    private String bio;

    /**
     * Gets the username.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the display name.
     *
     * @return the display name of the user
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     *
     * @param displayName the display name to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the bio of the user.
     *
     * @return the bio of the user
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the bio of the user.
     *
     * @param bio the bio to set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }
}
