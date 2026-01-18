package se.jensen.meiying.socialapp.dto;

/**
 * Data Transfer Object (DTO) used when creating a new comment.
 * This class contains the data sent from the client when a user
 * wants to add a comment to a post.
 */
public class CommentRequestDto {

    /**
     * The text content of the comment.
     */
    private String commentText;

    /**
     * The ID of the user creating the comment.
     */
    private Long userId;

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
     * Returns the ID of the user creating the comment.
     *
     * @return the user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user creating the comment.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
