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

    /**
     * Constructor for UserController.
     *
     * @param userService       Service for user-related operations.
     * @param postService       Service for post-related operations.
     * @param friendshipService Service for managing friendships.
     * @param jwtUtil           Utility for JWT token generation and validation.
     */
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

    /**
     * Authenticates a user and returns JWT tokens if successful.
     *
     * @param loginRequest The login credentials.
     * @return JWT token, refresh token, and authentication status.
     */
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

    /**
     * Refreshes JWT token using a valid refresh token.
     *
     * @param refreshToken The refresh token.
     * @return New JWT token and refresh token if valid, UNAUTHORIZED otherwise.
     */
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

    /**
     * Creates a new user.
     *
     * @param registrationDTO The user registration data.
     * @return The created user's DTO or error response.
     */
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

    /**
     * Retrieves all users.
     *
     * @return List of user DTOs.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(DTOMapper::toUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The user's ID.
     * @return User DTO or NOT FOUND if user does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = DTOMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Updates a user's information.
     *
     * @param id        The user's ID.
     * @param updateDTO Data to update.
     * @return Updated user DTO or appropriate error response.
     */
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
     * Deletes a user by ID.
     *
     * @param id The user's ID.
     * @return No content if deleted or NOT FOUND/ERROR status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id,
                                                          @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            // Optional: Add authorization check
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.getUsernameFromToken(token);
                    User currentUser = userService.findByUsername(username).orElse(null);

                    // Check if user is deleting themselves or is admin
                    if (currentUser != null &&
                            (currentUser.getId().equals(id) || "ADMIN".equals(currentUser.getRole()))) {
                        userService.deleteUser(id);

                        Map<String, String> response = new HashMap<>();
                        response.put("message", "User deleted successfully");
                        return ResponseEntity.ok(response);
                    } else {
                        Map<String, String> response = new HashMap<>();
                        response.put("message", "Unauthorized: You can only delete your own account");
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                    }
                }
            }

            // Fallback for testing or if no auth header
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);

        } catch (NoSuchElementException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            logger.error("Error deleting user with id: " + id, e);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error deleting user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Deletes a user along with all their posts.
     *
     * @param id The user's ID.
     * @return No content if deleted, NOT FOUND if user does not exist.
     */
    @DeleteMapping("/{id}/with-posts")
    public ResponseEntity<Void> deleteUserWithPosts(@PathVariable Long id) {
        try {
            userService.deleteUserWithAllPosts(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new post for a specific user.
     *
     * @param userId     The ID of the user creating the post.
     * @param requestDto Post content.
     * @return The created post DTO.
     */
    @PostMapping("/{userId}/posts")
    public ResponseEntity<PostResponseDto> createPostForUser(
            @PathVariable Long userId,
            @RequestBody PostRequestDto requestDto) {

        Post post = postService.createPost(userId, requestDto.getContent());
        PostResponseDto responseDto = DTOMapper.toPostResponseDto(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Retrieves accepted friends of a user.
     *
     * @param id The user's ID.
     * @return List of friend user DTOs.
     */
    @GetMapping("/{id}/friends")
    public ResponseEntity<List<UserDTO>> getFriends(@PathVariable Long id) {

        List<UserDTO> friends = friendshipService.getAcceptedFriends(id)
                .stream()
                .map(DTOMapper::toUserDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(friends);
    }
}
