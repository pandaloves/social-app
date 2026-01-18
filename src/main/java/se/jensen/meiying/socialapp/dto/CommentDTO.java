package se.jensen.meiying.socialapp.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) used to represent a comment.
 * This class is used to transfer comment data between the backend and frontend
 * without exposing the internal entity structure.
 */
public class CommentDTO {

    /**
     * Unique identifier for the comment.
     */
    private Long id;

    /**
     * The content/text of the comment.
     */
    private String text;

    /**
     * Timestamp representing when the comment was created.
     */
    private LocalDateTime createdAt;

    /**
     * Information about the author of the comment.
     */
    private UserInfoDTO author;

    /**
     * Default constructor.
     * Required for serialization and deserialization.
     */
    public CommentDTO() {
    }

    /**
     * Constructor used to create a fully populated CommentDTO.
     *
     * @param id        The unique ID of the comment.
     * @param text      The comment content.
     * @param createdAt The time when the comment was created.
     * @param author    Information about the comment author.
     */
    public CommentDTO(Long id, String text, LocalDateTime createdAt, UserInfoDTO author) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.author = author;
    }

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
    public String getText() {
        return text;
    }

    /**
     * Sets the comment text.
     *
     * @param text the comment content to set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the creation timestamp of the comment.
     *
     * @return the creation time.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the comment.
     *
     * @param createdAt the time the comment was created.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the author of the comment.
     *
     * @return the author information.
     */
    public UserInfoDTO getAuthor() {
        return author;
    }

    /**
     * Sets the author of the comment.
     *
     * @param author the author information to set.
     */
    public void setAuthor(UserInfoDTO author) {
        this.author = author;
    }
}
