package se.jensen.meiying.socialapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {
    private Long userId;
    private String username;
    private String displayName;
    private String role;
    private boolean success;

    public LoginResponseDTO() {}

    public LoginResponseDTO(Long userId, String username, String displayName, String role, boolean success) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.role = role;
        this.success = success;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRole() {
        return role;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", role='"+ role + '\'' +
                ", success=" + success +
                '}';
    }
}