package se.jensen.meiying.socialapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object (DTO) representing basic information about a user.
 * <p>
 * Used in API responses where only essential user information is needed,
 * such as user ID, username, display name, and profile image path.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDTO {

    /**
     * Unique identifier of the user
     */
    private Long id;

    /**
     * Username of the user
     */
    private String username;

    /**
     * Display name of the user
     */
    private String displayName;

    /**
     * Path or URL to the user's profile image
     */
    private String profileImagePath;

    /**
     * Default constructor.
     * Required for JSON deserialization and frameworks.
     */
    public UserInfoDTO() {
    }

    /**
     * Parameterized constructor to create a UserInfoDTO with all fields.
     *
     * @param id               unique identifier of the user
     * @param username         username of the user
     * @param displayName      display name of the user
     * @param profileImagePath path or URL to the user's profile image
     */
    public UserInfoDTO(Long id, String username, String displayName, String profileImagePath) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.profileImagePath = profileImagePath;
    }

    /**
     * Gets the user's ID.
     *
     * @return the unique identifier of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     *
     * @param id the unique identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * Gets the profile image path or URL.
     *
     * @return the profile image path of the user
     */
    public String getProfileImagePath() {
        return profileImagePath;
    }

    /**
     * Sets the profile image path or URL.
     *
     * @param profileImagePath the profile image path to set
     */
    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
