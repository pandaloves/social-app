package se.jensen.meiying.socialapp.dto;

/**
 * Data Transfer Object for creating or updating a post.
 * <p>
 * Contains the content of the post and the ID of the user who creates it.
 */
public class PostRequestDto {

    /**
     * Text content of the post.
     */
    private String content;

    /**
     * ID of the user creating the post.
     */
    private Long userId;

    /**
     * Returns the content of the post.
     *
     * @return the post content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the post.
     *
     * @param content the post content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the ID of the user creating the post.
     *
     * @return user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user creating the post.
     *
     * @param userId user ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
