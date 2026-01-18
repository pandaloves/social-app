package se.jensen.meiying.socialapp.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for sending post information in responses.
 * <p>
 * Contains the post ID, content, creation timestamp, and author details.
 */
public class PostResponseDto {

    /**
     * The unique ID of the post.
     */
    private Long id;

    /**
     * The textual content of the post.
     */
    private String content;

    /**
     * The timestamp when the post was created.
     */
    private LocalDateTime createdAt;

    /**
     * The author of the post represented as a UserDTO.
     */
    private UserDTO author;

    /**
     * Returns the post ID.
     *
     * @return the ID of the post
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the post ID.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the creation timestamp of the post.
     *
     * @return the creation time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the post.
     *
     * @param createdAt the timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the author of the post.
     *
     * @return the post author
     */
    public UserDTO getAuthor() {
        return author;
    }

    /**
     * Sets the author of the post.
     *
     * @param author the author to set
     */
    public void setAuthor(UserDTO author) {
        this.author = author;
    }
}
