package se.jensen.meiying.socialapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.dto.*;
import se.jensen.meiying.socialapp.security.JwtUtil;
import se.jensen.meiying.socialapp.service.FriendshipService;
import se.jensen.meiying.socialapp.service.UserService;
import se.jensen.meiying.socialapp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final FriendshipService friendshipService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService,
                          PostService postService,
                          FriendshipService friendshipService,
                          JwtUtil jwtUtil
    ) {
        this.userService = userService;
        this.postService = postService;
        this.friendshipService = friendshipService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(
                loginRequest.getUsername(), loginRequest.getPassword());

        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new JwtResponseDTO(null, null, false));
        }

        User user = userService.findByUsername(loginRequest.getUsername()).get();
        String token = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        return ResponseEntity.ok(new JwtResponseDTO(token, refreshToken, true));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponseDTO> refreshToken(@RequestParam String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String username = jwtUtil.getUsernameFromToken(refreshToken);
        String newToken = jwtUtil.generateToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        return ResponseEntity.ok(new JwtResponseDTO(newToken, newRefreshToken, true));
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRegistrationDTO registrationDTO) {
        try {
            User user = userService.createUser(registrationDTO);
            UserDTO userDTO = DTOMapper.toUserDTO(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(DTOMapper::toUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = DTOMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserRegistrationDTO updateDTO) {
        try {
            User user = userService.updateUser(id, updateDTO);
            UserDTO userDTO = DTOMapper.toUserDTO(user);
            return ResponseEntity.ok(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/with-posts")
    public ResponseEntity<UserWithPostsResponseDto> getUserWithPosts(@PathVariable("id") Long userId) {
        User user = userService.getUserWithPosts(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Post> sortedPosts = user.getPosts().stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .collect(Collectors.toList());

        user.setPosts(sortedPosts);

        UserWithPostsResponseDto responseDto = DTOMapper.toUserWithPostsResponseDto(user);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}/with-posts")
    public ResponseEntity<Void> deleteUserWithPosts(@PathVariable Long id) {
        try {
            userService.deleteUserWithAllPosts(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponseDto> createPostForUser(
            @PathVariable Long userId,
            @RequestBody PostRequestDto requestDto) {

        Post post = postService.createPost(userId, requestDto.getContent());
        PostResponseDto responseDto = DTOMapper.toPostResponseDto(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<UserDTO>> getFriends(@PathVariable Long id) {

        List<UserDTO> friends = friendshipService.getAcceptedFriends(id)
                .stream()
                .map(DTOMapper::toUserDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(friends);
    }

    @GetMapping("/user/{username}/wall")
    public ResponseEntity<PageResponseDTO<PostDTO>> getUserWall(
            @PathVariable String username,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Post> page = postService.getUserWall(username, pageable);

        List<PostDTO> postDTOs = page.getContent().stream()
                .map(DTOMapper::toPostDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageResponseDTO<>(
                postDTOs,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        ));
    }
}