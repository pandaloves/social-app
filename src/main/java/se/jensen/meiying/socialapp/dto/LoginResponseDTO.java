package se.jensen.meiying.socialapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object used to return login results to the client.
 * <p>
 * This DTO contains user information and authentication status
 * after a successful or failed login attempt.
 * Null values will be excluded from JSON responses.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {

    /**
     * Unique ID of the authenticated user.
     */
    private Long userId;

    /**
     * Username of the authenticated user.
     */
    private String username;

    /**
     * Display name shown in the user interface.
     */
    private String displayName;

    /**
     * Role assigned to the user (for example: USER or ADMIN).
     */
    private String role;

    /**
     * Indicates whether the login attempt was successful.
     */
    private boolean success;

    /**
     * Default constructor.
     */
    public LoginResponseDTO() {
    }

    /**
     * Constructor used to create a complete login response.
     *
     * @param userId      unique user identifier
     * @param username    account username
     * @param displayName user's display name
     * @param role        user's role
     * @param success     login result status
     */
    public LoginResponseDTO(Long userId, String username, String displayName, String role, boolean success) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.role = role;
        this.success = success;
    }

    /**
     * Returns the user ID.
     *
     * @return user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Returns the username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the display name.
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the user role.
     *
     * @return role name
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns whether login was successful.
     *
     * @return true if login succeeded, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the user ID.
     *
     * @param userId unique user identifier
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Sets the username.
     *
     * @param username account username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the display name.
     *
     * @param displayName user's display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the user role.
     *
     * @param role role name
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Sets the login result status.
     *
     * @param success true if login succeeded
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns a string representation of the login response.
     *
     * @return formatted login response string
     */
    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", role='" + role + '\'' +
                ", success=" + success +
                '}';
    }
}
