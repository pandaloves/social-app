package se.jensen.meiying.socialapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.meiying.socialapp.dto.*;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/feed")
    public ResponseEntity<PageResponseDTO<PostDTO>> getFeed(
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Post> page = postService.getFeed(pageable);

        List<PostDTO> content = page.getContent()
                .stream()
                .map(DTOMapper::toPostDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageResponseDTO<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        ));
    }

    @GetMapping("/user/{username}/wall")
    public ResponseEntity<PageResponseDTO<PostDTO>> getUserWall(
            @PathVariable String username,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Post> page = postService.getUserWall(username, pageable);

        List<PostDTO> content = page.getContent()
                .stream()
                .map(DTOMapper::toPostDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageResponseDTO<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return post == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(DTOMapper.toPostDTO(post));
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostRequest request) {
        Post post = postService.createPost(request.getUserId(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(DTOMapper.toPostDTO(post));
    }

    static class CreatePostRequest {
        private Long userId;
        private String content;
        public Long getUserId() { return userId; }
        public String getContent() { return content; }
    }
}
