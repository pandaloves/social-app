package se.jensen.meiying.socialapp.dto;

/**
 * Data Transfer Object used to send authentication results
 * back to the client after a successful or failed login attempt.
 * <p>
 * This DTO contains the generated JWT access token,
 * refresh token and authentication status.
 */
public class JwtResponseDTO {

    /**
     * JWT access token used for authenticated API requests.
     */
    private String token;

    /**
     * JWT refresh token used to obtain a new access token.
     */
    private String refreshToken;

    /**
     * Indicates whether authentication was successful.
     */
    private boolean success;

    /**
     * Creates a new JwtResponseDTO.
     *
     * @param token        JWT access token
     * @param refreshToken JWT refresh token
     * @param success      authentication result status
     */
    public JwtResponseDTO(String token, String refreshToken, boolean success) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.success = success;
    }

    /**
     * Returns the JWT access token.
     *
     * @return access token
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the JWT refresh token.
     *
     * @return refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Returns authentication success status.
     *
     * @return true if authentication succeeded, otherwise false
     */
    public boolean isSuccess() {
        return success;
    }
}
