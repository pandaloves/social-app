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

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

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
