package se.jensen.meiying.socialapp.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing a post.
 * <p>
 * Used to send post data from the backend to the client,
 * including the post content, author information, creation time,
 * and associated comments.
 */
public class PostDTO {

    /**
     * Unique identifier of the post.
     */
    private Long id;

    /**
     * Text content of the post.
     */
    private String content;

    /**
     * Timestamp when the post was created.
     */
    private LocalDateTime createdAt;

    /**
     * Information about the user who created the post.
     */
    private UserInfoDTO user;

    /**
     * List of comments associated with this post.
     */
    private List<CommentDTO> comments = new ArrayList<>();

    /**
     * Default constructor.
     */
    public PostDTO() {
    }

    /**
     * Creates a new PostDTO with basic information.
     *
     * @param id        the unique identifier of the post
     * @param content   the text content of the post
     * @param createdAt the creation timestamp
     * @param user      the author of the post
     */
    public PostDTO(Long id, String content, LocalDateTime createdAt, UserInfoDTO user) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }

    /**
     * Returns the post ID.
     *
     * @return post ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the post ID.
     *
     * @param id post ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the content of the post.
     *
     * @return post content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the post.
     *
     * @param content post content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the creation timestamp of the post.
     *
     * @return creation time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the post.
     *
     * @param createdAt creation time
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the author information of the post.
     *
     * @return user info
     */
    public UserInfoDTO getUser() {
        return user;
    }

    /**
     * Sets the author information of the post.
     *
     * @param user user info
     */
    public void setUser(UserInfoDTO user) {
        this.user = user;
    }

    /**
     * Returns the list of comments associated with the post.
     *
     * @return list of comments
     */
    public List<CommentDTO> getComments() {
        return comments;
    }

    /**
     * Sets the list of comments associated with the post.
     *
     * @param comments list of comments
     */
    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
