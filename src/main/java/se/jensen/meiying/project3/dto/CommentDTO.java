package se.jensen.meiying.project3.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private UserInfoDTO author;

    public CommentDTO() {}

    public CommentDTO(Long id, String text, LocalDateTime createdAt, UserInfoDTO author) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.author = author;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public UserInfoDTO getAuthor() { return author; }
    public void setAuthor(UserInfoDTO author) { this.author = author; }
}