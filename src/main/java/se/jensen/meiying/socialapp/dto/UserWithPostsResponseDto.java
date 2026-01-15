package se.jensen.meiying.socialapp.dto;

import java.util.List;

public class UserWithPostsResponseDto {
    private UserDTO user;
    private List<PostResponseDto> posts;

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

    public List<PostResponseDto> getPosts() { return posts; }
    public void setPosts(List<PostResponseDto> posts) { this.posts = posts; }
}