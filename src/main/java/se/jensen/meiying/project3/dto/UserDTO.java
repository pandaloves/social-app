package se.jensen.meiying.project3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String displayName;
    private String bio;
    private String profileImagePath;

    public UserDTO() {}

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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getProfileImagePath() { return profileImagePath; }
    public void setProfileImagePath(String profileImagePath) { this.profileImagePath = profileImagePath; }
}