package se.jensen.meiying.socialapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.meiying.socialapp.dto.DTOMapper;
import se.jensen.meiying.socialapp.dto.PostRequestDto;
import se.jensen.meiying.socialapp.dto.PostResponseDto;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.service.PostService;

import java.util.NoSuchElementException;

/**
 * REST controller for managing posts in the social app.
 * Provides endpoints to get feeds, user walls, individual posts,
 * as well as create, update, and delete posts.
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Retrieves posts, optionally filtered by user.
     *
     * @param userId   optional ID of the user to filter posts
     * @param pageable pagination information
     * @return a paginated response of PostResponseDto objects
     */
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

    /**
     * Retrieves a post by its ID.
     *
     * @param id the ID of the post
     * @return the PostResponseDto if found, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return post == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(DTOMapper.toPostResponseDto(post));
    }

    /**
     * Creates a new post for a user.
     *
     * @param requestDto the post data containing userId and content
     * @return the created PostResponseDto wrapped in ResponseEntity
     */
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto) {
        Post post = postService.createPost(requestDto.getUserId(), requestDto.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(DTOMapper.toPostResponseDto(post));
    }

    /**
     * Updates the content of an existing post.
     *
     * @param id         the ID of the post to update
     * @param requestDto the new post data
     * @return the updated PostResponseDto or appropriate error status
     */
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

    /**
     * Deletes a post by its ID.
     *
     * @param id the ID of the post to delete
     * @return 204 No Content if deleted, 404 Not Found if post does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean deleted = postService.deletePost(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
