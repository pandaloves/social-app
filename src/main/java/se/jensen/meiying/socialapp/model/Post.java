package se.jensen.meiying.socialapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a social media post created by a user.
 * <p>
 * Each post has content, a creation timestamp, an author (User),
 * and a list of associated comments.
 */
@Entity
@Table(name = "post")
public class Post {

    /**
     * Unique identifier for the post.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Text content of the post, max 500 characters.
     */
    @Column(nullable = false, length = 500)
    private String content;

    /**
     * Timestamp of when the post was created.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * The user who created the post.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"posts", "password", "email"})
    private User user;

    /**
     * List of comments associated with the post.
     */
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Comment> comments = new ArrayList<>();

    /**
     * Default constructor, sets the creation timestamp to now.
     */
    public Post() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Creates a new post with content and author.
     *
     * @param content The text content of the post.
     * @param user    The user creating the post.
     */
    public Post(String content, User user) {
        this.content = content;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Adds a comment to the post.
     *
     * @param comment The comment to add.
     */
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    /**
     * Removes a comment from the post.
     *
     * @param comment The comment to remove.
     */
    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

    /**
     * @return The post ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The post ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The content of the post.
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content of the post to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The creation timestamp of the post.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The creation timestamp to set.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The user who created the post.
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user to set as the author of the post.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return The list of comments associated with the post.
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments The list of comments to set for the post.
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
