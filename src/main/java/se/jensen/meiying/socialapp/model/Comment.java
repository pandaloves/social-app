package se.jensen.meiying.socialapp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Represents a comment made by a user on a post.
 * <p>
 * Each comment is associated with a single author (User) and a single post (Post).
 * The creation timestamp is automatically set when a new Comment is instantiated.
 */
@Entity
@Table(name = "comment")
public class Comment {

    /**
     * The unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The textual content of the comment, limited to 300 characters.
     */
    @Column(nullable = false, length = 300)
    private String text;

    /**
     * Timestamp when the comment was created. Automatically set in the default constructor.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * The user who authored the comment.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    /**
     * The post that the comment belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * Default constructor that sets the creation timestamp to the current time.
     */
    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * @return the unique identifier of the comment
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the unique identifier to set for the comment
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the textual content of the comment
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the textual content to set for the comment
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the creation timestamp of the comment
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the creation timestamp to set for the comment
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the user who authored the comment
     */
    public User getAuthor() {
        return author;
    }

    /**
     * @param author the user who authored the comment
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * @return the post that this comment belongs to
     */
    public Post getPost() {
        return post;
    }

    /**
     * @param post the post to associate this comment with
     */
    public void setPost(Post post) {
        this.post = post;
    }
}
