package se.jensen.meiying.socialapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.meiying.socialapp.dto.*;
import se.jensen.meiying.socialapp.logging.AppLogger;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.security.JwtUtil;
import se.jensen.meiying.socialapp.service.FriendshipService;
import se.jensen.meiying.socialapp.service.PostService;
import se.jensen.meiying.socialapp.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * REST controller for managing users, authentication, and user-related actions.
 * Handles login, token refresh, user CRUD operations, retrieving posts and friends.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final FriendshipService friendshipService;
    private AppLogger logger;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService,
                          PostService postService,
                          FriendshipService friendshipService,
                          AppLogger logger,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.postService = postService;
        this.friendshipService = friendshipService;
        this.logger = new AppLogger();
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

    /**
     * SIMPLIFIED: Deletes a user by ID only - no authorization required
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        try {
            logger.info("Deleting user with ID: " + id);

            // Simply delete the user by ID
            userService.deleteUser(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            response.put("userId", id.toString());

            return ResponseEntity.ok(response);

        } catch (NoSuchElementException e) {
            logger.warn("User not found with ID: " + id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found with id: " + id);
            response.put("error", "NOT_FOUND");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {
            logger.error("Error deleting user with ID: " + id, e);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Error deleting user");
            response.put("error", e.getMessage());
            response.put("userId", id.toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}/with-posts")
    public ResponseEntity<Map<String, String>> deleteUserWithPosts(@PathVariable Long id) {
        try {
            userService.deleteUserWithAllPosts(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User and all posts deleted successfully");
            response.put("userId", id.toString());

            return ResponseEntity.ok(response);

        } catch (NoSuchElementException e) {
            logger.warn("User not found for deletion with posts, ID: " + id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found with id: " + id);
            response.put("error", "NOT_FOUND");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {
            logger.error("Error deleting user with posts, ID: " + id, e);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Error deleting user with posts");
            response.put("error", e.getMessage());
            response.put("userId", id.toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/{userId}/posts")
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
}