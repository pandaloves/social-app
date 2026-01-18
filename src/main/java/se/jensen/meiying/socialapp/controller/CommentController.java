package se.jensen.meiying.socialapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.meiying.socialapp.dto.CommentRequestDto;
import se.jensen.meiying.socialapp.dto.CommentResponseDto;
import se.jensen.meiying.socialapp.dto.DTOMapper;
import se.jensen.meiying.socialapp.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing comments on posts.
 * Provides endpoints to create and retrieve comments for a given post.
 */
@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * Constructor to inject CommentService.
     *
     * @param commentService the service handling comment operations
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Creates a new comment for a specific post.
     *
     * @param postId     the ID of the post to comment on
     * @param requestDto the DTO containing user ID and comment text
     * @return the created comment wrapped in ResponseEntity
     */
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto requestDto) {

        var comment = commentService.createComment(
                postId,
                requestDto.getUserId(),
                requestDto.getCommentText()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DTOMapper.toCommentResponseDto(comment));
    }

    /**
     * Retrieves all comments for a specific post.
     *
     * @param postId the ID of the post
     * @return list of CommentResponseDto wrapped in ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getCommentsForPost(
            @PathVariable Long postId) {

        List<CommentResponseDto> comments = commentService.getCommentsForPost(postId)
                .stream()
                .map(DTOMapper::toCommentResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }
}
