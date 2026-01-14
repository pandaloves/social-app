package se.jensen.meiying.socialapp.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserInfoDTO user;
    private List<CommentDTO> comments = new ArrayList<>();  // 添加评论列表

    public PostDTO() {}

    public PostDTO(Long id, String content, LocalDateTime createdAt, UserInfoDTO user) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public UserInfoDTO getUser() { return user; }
    public void setUser(UserInfoDTO user) { this.user = user; }

    public List<CommentDTO> getComments() { return comments; }
    public void setComments(List<CommentDTO> comments) { this.comments = comments; }
}