package se.jensen.meiying.project3.dto;

public class JwtResponseDTO {
    private String token;
    private String refreshToken;
    private boolean success;

    public JwtResponseDTO(String token, String refreshToken, boolean success) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.success = success;
    }

    public String getToken() { return token; }
    public String getRefreshToken() { return refreshToken; }
    public boolean isSuccess() { return success; }
}
