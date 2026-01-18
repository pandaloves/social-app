package se.jensen.meiying.socialapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object for transferring user information.
 * <p>
 * Contains user identification, username, email, role, display name,
 * biography, and profile image path.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    /**
     * The unique ID of the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The role of the user (e.g., USER, ADMIN).
     */
    private String role;

    /**
     * The display name of the user.
     */
    private String displayName;

    /**
     * The biography or description of the user.
     */
    private String bio;

    /**
     * Path or URL to the user's profile image.
     */
    private String profileImagePath;

    /**
     * Default no-args constructor.
     */
    public UserDTO() {
    }

    /**
     * Constructs a new UserDTO with all fields.
     *
     * @param id               the user ID
     * @param username         the username
     * @param email            the email address
     * @param role             the user's role
     * @param displayName      the display name
     * @param bio              the biography
     * @param profileImagePath path to the profile image
     */
    public UserDTO(Long id, String username, String email, String role,
                   String displayName, String bio, String profileImagePath) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.displayName = displayName;
        this.bio = bio;
        this.profileImagePath = profileImagePath;
    }

    /**
     * Returns the user ID.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the username.
     *
     * @return the username
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
     * Returns the email address.
     *
     * @return the email
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
     * Returns the user role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user role.
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the display name of the user.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name of the user.
     *
     * @param displayName the display name to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the biography of the user.
     *
     * @return the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the biography of the user.
     *
     * @param bio the bio to set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Returns the profile image path.
     *
     * @return the profileImagePath
     */
    public String getProfileImagePath() {
        return profileImagePath;
    }

    /**
     * Sets the profile image path.
     *
     * @param profileImagePath the path to set
     */
    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
