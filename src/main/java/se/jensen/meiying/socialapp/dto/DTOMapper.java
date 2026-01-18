package se.jensen.meiying.socialapp.dto;

import se.jensen.meiying.socialapp.model.Comment;
import se.jensen.meiying.socialapp.model.Friendship;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between entity models and DTOs.
 * <p>
 * Provides static methods to convert Post, Comment, User, and Friendship
 * entities into their corresponding Data Transfer Objects (DTOs) for API responses.
 */
public class DTOMapper {

    /**
     * Converts a Post entity to a PostDTO.
     *
     * @param post the Post entity to convert
     * @return the corresponding PostDTO, or null if input is null
     */
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

    /**
     * Converts a Comment entity to a CommentDTO.
     *
     * @param comment the Comment entity to convert
     * @return the corresponding CommentDTO, or null if input is null
     */
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

    /**
     * Converts a User entity to a lightweight UserInfoDTO.
     *
     * @param user the User entity to convert
     * @return the corresponding UserInfoDTO, or null if input is null
     */
    public static UserInfoDTO toUserInfoDTO(User user) {
        if (user == null) return null;

        UserInfoDTO dto = new UserInfoDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setDisplayName(user.getDisplayName());
        dto.setProfileImagePath(user.getProfileImagePath());
        return dto;
    }

    /**
     * Converts a User entity to a full UserDTO with all details.
     *
     * @param user the User entity to convert
     * @return the corresponding UserDTO, or null if input is null
     */
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

    /**
     * Converts a Comment entity to a CommentResponseDto for API responses.
     *
     * @param comment the Comment entity to convert
     * @return the corresponding CommentResponseDto, or null if input is null
     */
    public static CommentResponseDto toCommentResponseDto(Comment comment) {
        if (comment == null) return null;

        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setCommentText(comment.getText());
        dto.setTimestamp(comment.getCreatedAt());
        dto.setUser(toUserInfoDTO(comment.getAuthor()));

        return dto;
    }

    /**
     * Converts a Friendship entity to a FriendshipResponseDto for API responses.
     *
     * @param friendship the Friendship entity to convert
     * @return the corresponding FriendshipResponseDto
     */
    public static FriendshipResponseDto toFriendshipResponseDto(Friendship friendship) {
        FriendshipResponseDto dto = new FriendshipResponseDto();
        dto.setId(friendship.getId());
        dto.setRequester(toUserInfoDTO(friendship.getRequester()));
        dto.setAddressee(toUserInfoDTO(friendship.getAddressee()));
        dto.setStatus(friendship.getStatus());
        return dto;
    }

    /**
     * Converts a User entity with posts to a UserWithPostsResponseDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserWithPostsResponseDto
     */
    public static UserWithPostsResponseDto toUserWithPostsResponseDto(User user) {
        UserWithPostsResponseDto dto = new UserWithPostsResponseDto();
        dto.setUser(toUserDTO(user));

        List<PostResponseDto> postDtos = user.getPosts().stream()
                .map(DTOMapper::toPostResponseDto)
                .collect(Collectors.toList());

        dto.setPosts(postDtos);
        return dto;
    }

    /**
     * Converts a Post entity to a PostResponseDto for API responses.
     *
     * @param post the Post entity to convert
     * @return the corresponding PostResponseDto
     */
    public static PostResponseDto toPostResponseDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setAuthor(toUserDTO(post.getUser()));
        return dto;
    }
}
