package se.jensen.meiying.socialapp.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) used for sending comment data
 * from the backend to the client.
 * This class represents a comment together with its metadata
 * such as timestamp and user information.
 */
public class CommentResponseDto {

    /**
     * Unique identifier of the comment.
     */
    private Long id;

    /**
     * The text content of the comment.
     */
    private String commentText;

    /**
     * The timestamp when the comment was created.
     */
    private LocalDateTime timestamp;

    /**
     * Information about the user who created the comment.
     */
    private UserInfoDTO user;

    /**
     * Returns the comment ID.
     *
     * @return the comment ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the comment ID.
     *
     * @param id the comment ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the comment text.
     *
     * @return the comment content.
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Sets the comment text.
     *
     * @param commentText the comment content to set.
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     * Returns the creation timestamp of the comment.
     *
     * @return the timestamp when the comment was created.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the comment.
     *
     * @param timestamp the creation time to set.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the user who created the comment.
     *
     * @return the user information.
     */
    public UserInfoDTO getUser() {
        return user;
    }

    /**
     * Sets the user who created the comment.
     *
     * @param user the user information to set.
     */
    public void setUser(UserInfoDTO user) {
        this.user = user;
    }
}
