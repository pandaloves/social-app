package se.jensen.meiying.socialapp.dto;

import se.jensen.meiying.socialapp.model.Friendship;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {

    public static PostDTO toPostDTO(Post post) {
        if (post == null) return null;

        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());

        if (post.getUser() != null) {
            dto.setUser(toUserInfoDTO(post.getUser()));
        }

        if (post.getComments() != null && !post.getComments().isEmpty()) {
            List<CommentDTO> commentDTOs = post.getComments().stream()
                    .map(DTOMapper::toCommentDTO)
                    .collect(Collectors.toList());
            dto.setComments(commentDTOs);
        }

        return dto;
    }

    public static CommentDTO toCommentDTO(Comment comment) {
        if (comment == null) return null;

        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setCreatedAt(comment.getCreatedAt());

        if (comment.getAuthor() != null) {
            dto.setAuthor(toUserInfoDTO(comment.getAuthor()));
        }

        return dto;
    }

    public static UserInfoDTO toUserInfoDTO(User user) {
        if (user == null) return null;

        UserInfoDTO dto = new UserInfoDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setDisplayName(user.getDisplayName());
        dto.setProfileImagePath(user.getProfileImagePath());
        return dto;
    }

    public static UserDTO toUserDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setDisplayName(user.getDisplayName());
        dto.setBio(user.getBio());
        dto.setProfileImagePath(user.getProfileImagePath());
        return dto;
    }

    public static CommentResponseDto toCommentResponseDto(Comment comment) {
        if (comment == null) return null;

        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setCommentText(comment.getText());
        dto.setTimestamp(comment.getCreatedAt());
        dto.setUser(toUserInfoDTO(comment.getAuthor()));

        return dto;
    }

    public static FriendshipResponseDto toFriendshipResponseDto(Friendship friendship) {
        FriendshipResponseDto dto = new FriendshipResponseDto();
        dto.setId(friendship.getId());
        dto.setRequester(toUserInfoDTO(friendship.getRequester()));
        dto.setAddressee(toUserInfoDTO(friendship.getAddressee()));
        dto.setStatus(friendship.getStatus());
        return dto;
    }

    public static UserWithPostsResponseDto toUserWithPostsResponseDto(User user) {
        UserWithPostsResponseDto dto = new UserWithPostsResponseDto();
        dto.setUser(toUserDTO(user));

        List<PostResponseDto> postDtos = user.getPosts().stream()
                .map(DTOMapper::toPostResponseDto)
                .collect(Collectors.toList());

        dto.setPosts(postDtos);
        return dto;
    }

    public static PostResponseDto toPostResponseDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setAuthor(toUserDTO(post.getUser()));
        return dto;
    }
}