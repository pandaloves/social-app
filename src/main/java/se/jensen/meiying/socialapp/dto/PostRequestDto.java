package se.jensen.meiying.socialapp.dto;

public class PostRequestDto {
    private String content;
    private Long userId;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}