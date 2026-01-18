package se.jensen.meiying.socialapp.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a user along with their posts.
 * <p>
 * Contains user details and a list of posts authored by the user.
 */
public class UserWithPostsResponseDto {

    /**
     * The user details
     */
    private UserDTO user;

    /**
     * List of posts created by the user
     */
    private List<PostResponseDto> posts;

    /**
     * Gets the user details.
     *
     * @return the user
     */
    public UserDTO getUser() {
        return user;
    }

    /**
     * Sets the user details.
     *
     * @param user the user to set
     */
    public void setUser(UserDTO user) {
        this.user = user;
    }

    /**
     * Gets the list of posts authored by the user.
     *
     * @return list of posts
     */
    public List<PostResponseDto> getPosts() {
        return posts;
    }

    /**
     * Sets the list of posts authored by the user.
     *
     * @param posts the list of posts to set
     */
    public void setPosts(List<PostResponseDto> posts) {
        this.posts = posts;
    }
}
