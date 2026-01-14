package se.jensen.meiying.socialapp.dto;

import java.time.LocalDateTime;

public class CommentResponseDto {

    private Long id;
    private String commentText;
    private LocalDateTime timestamp;
    private UserInfoDTO user;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public UserInfoDTO getUser() { return user; }
    public void setUser(UserInfoDTO user) { this.user = user; }
}
