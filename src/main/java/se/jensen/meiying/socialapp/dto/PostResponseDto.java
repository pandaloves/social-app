package se.jensen.meiying.socialapp.dto;

import java.time.LocalDateTime;

public class PostResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserDTO author;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public UserDTO getAuthor() { return author; }
    public void setAuthor(UserDTO author) { this.author = author; }
}