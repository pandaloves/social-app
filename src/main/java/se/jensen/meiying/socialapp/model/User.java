package se.jensen.meiying.socialapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the social app.
 * <p>
 * Each user has a unique username and email, a role,
 * display name, bio, profile image path, and can have multiple posts and comments.
 */
@Entity
@Table(name = "app_user")
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username of the user.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Unique email of the user.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Password of the user (should be stored hashed).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Role of the user (e.g., ROLE_USER, ROLE_ADMIN).
     */
    @Column(nullable = false)
    private String role;

    /**
     * Display name of the user.
     */
    @Column(name = "display_name", nullable = false)
    private String displayName;

    /**
     * Short bio of the user.
     */
    @Column(nullable = false)
    private String bio;

    /**
     * Path to the profile image of the user.
     */
    @Column(name = "profile_image_path")
    private String profileImagePath;

    /**
     * List of posts created by the user.
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    /**
     * List of comments created by the user.
     */
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Comment> comments = new ArrayList<>();

    /**
     * Adds a post to the user's list of posts.
     *
     * @param post The post to add.
     */
    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    /**
     * Removes a post from the user's list of posts.
     *
     * @param post The post to remove.
     */
    public void removePost(Post post) {
        posts.remove(post);
        post.setUser(null);
    }

    /**
     * @return The user's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The user's ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role The role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return The display name of the user.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName The display name to set.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return The bio of the user.
     */
    public String getBio() {
        return bio;
    }

    /**
     * @param bio The bio to set.
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * @return The profile image path of the user.
     */
    public String getProfileImagePath() {
        return profileImagePath;
    }

    /**
     * @param profileImagePath The profile image path to set.
     */
    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    /**
     * @return The list of posts created by the user.
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * @param posts The list of posts to set for the user.
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * @return The list of comments created by the user.
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments The list of comments to set for the user.
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
