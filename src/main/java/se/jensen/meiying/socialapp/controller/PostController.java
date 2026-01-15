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
import java.util.NoSuchElementException;
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

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getPosts(
            @RequestParam(required = false) Long userId,
            @PageableDefault(page = 0, size = 10, sort = "createdAt,desc") Pageable pageable) {

        Page<Post> page;
        if (userId != null) {
            page = postService.getPostsByUserId(userId, pageable);
        } else {
            page = postService.getAllPosts(pageable);
        }

        Page<PostResponseDto> responsePage = page.map(DTOMapper::toPostResponseDto);
        return ResponseEntity.ok(responsePage);
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
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return post == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(DTOMapper.toPostResponseDto(post));
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto) {
        Post post = postService.createPost(requestDto.getUserId(), requestDto.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(DTOMapper.toPostResponseDto(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDto requestDto) {
        try {
            Post updatedPost = postService.updatePostContent(id, requestDto.getContent());
            return ResponseEntity.ok(DTOMapper.toPostResponseDto(updatedPost));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean deleted = postService.deletePost(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}