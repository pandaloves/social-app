package se.jensen.meiying.project3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDTO {
    private Long id;
    private String username;
    private String displayName;
    private String profileImagePath;

    public UserInfoDTO() {}

    public UserInfoDTO(Long id, String username, String displayName, String profileImagePath) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.profileImagePath = profileImagePath;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getProfileImagePath() { return profileImagePath; }
    public void setProfileImagePath(String profileImagePath) { this.profileImagePath = profileImagePath; }
}