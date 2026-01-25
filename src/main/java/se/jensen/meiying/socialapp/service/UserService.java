package se.jensen.meiying.socialapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.meiying.socialapp.dto.UserRegistrationDTO;
import se.jensen.meiying.socialapp.logging.AppLogger;
import se.jensen.meiying.socialapp.model.Comment;
import se.jensen.meiying.socialapp.model.Friendship;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.repository.CommentRepository;
import se.jensen.meiying.socialapp.repository.FriendshipRepository;
import se.jensen.meiying.socialapp.repository.PostRepository;
import se.jensen.meiying.socialapp.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * {@link UserService} hanterar logik relaterad till {@link User}-entiteter.
 * <p>
 * Service-klassen ansvarar för skapande, uppdatering, borttagning, autentisering och hämtning av användare,
 * samt hantering av användarens inlägg.
 * </p>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppLogger logger;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final FriendshipRepository friendshipRepository;

    /**
     * Skapar en ny instans av {@link UserService}.
     *
     * @param userRepository  repository för att hantera {@link User}-entiteter.
     * @param passwordEncoder {@link PasswordEncoder} för kryptering av lösenord.
     * @param logger          logger för applikationsloggning.
     */
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AppLogger logger,
                       PostRepository postRepository,
                       CommentRepository commentRepository,
                       FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.logger = logger;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.friendshipRepository = friendshipRepository;
    }

    /**
     * Skapar en ny användare baserat på ett {@link UserRegistrationDTO}.
     *
     * @param registrationDTO DTO med registreringsdata.
     * @return den skapade {@link User}-entiteten.
     * @throws IllegalArgumentException om användarnamn eller email redan finns.
     */
    @Transactional
    public User createUser(UserRegistrationDTO registrationDTO) {
        logger.info("Attempting to create user with username: " + registrationDTO.getUsername());

        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            logger.warn("User creation failed – username already exists: " + registrationDTO.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            logger.warn("User creation failed – email already exists: " + registrationDTO.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setDisplayName(registrationDTO.getDisplayName());
        user.setBio(registrationDTO.getBio());
        user.setRole("USER");

        User savedUser = userRepository.save(user);
        logger.info("User created successfully with id: " + savedUser.getId());

        return savedUser;
    }

    /**
     * Hämtar alla användare.
     *
     * @return en lista med alla {@link User}-entiteter.
     */
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    /**
     * Hämtar en användare baserat på ID.
     *
     * @param id ID för användaren.
     * @return den hittade {@link User}-entiteten eller {@code null} om användaren inte finns.
     */
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        logger.info("Fetching user by id: " + id);
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Hämtar en användare baserat på användarnamn.
     *
     * @param username användarnamnet.
     * @return en {@link Optional} med användaren om den finns.
     */
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        logger.info("Fetching user by username: " + username);
        return userRepository.findByUsername(username);
    }

    /**
     * Tar bort en användare baserat på ID.
     *
     * @param id ID för användaren som ska tas bort.
     * @throws NoSuchElementException om användaren inte hittas.
     */
    @Transactional
    public void deleteUser(Long id) {
        logger.info("Attempting to delete user with id: " + id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Delete failed – user not found with id: " + id);
                    return new NoSuchElementException("User not found");
                });

        // Step 1: Delete user's comments
        deleteUserComments(user);

        // Step 2: Delete user's posts (and their comments)
        deleteUserPosts(user);

        // Step 3: Delete user's friendships (both as requester and addressee)
        deleteUserFriendships(user);

        // Step 4: Finally delete the user
        userRepository.delete(user);

        logger.info("User and all related data deleted successfully with id: " + id);
    }


    /**
     * Deletes a user along with all their posts and friendships.
     *
     * @param id ID for the user to delete.
     * @throws NoSuchElementException if user not found.
     */
    @Transactional
    public void deleteUserWithAllPosts(Long id) {
        logger.info("Deleting user and all posts, userId: " + id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Delete with posts failed – user not found, userId: " + id);
                    return new NoSuchElementException("User not found");
                });

        // Step 1: Delete user's comments
        deleteUserComments(user);

        // Step 2: Delete user's posts (and their comments)
        deleteUserPosts(user);

        // Step 3: Delete user's friendships (both as requester and addressee)
        deleteUserFriendships(user);

        // Step 4: Finally delete the user
        userRepository.delete(user);

        logger.info("User and all related data deleted successfully, userId: " + id);
    }

    /**
     * Deletes all comments made by the user.
     */
    private void deleteUserComments(User user) {
        try {
            List<Comment> userComments = commentRepository.findByAuthor(user);
            if (!userComments.isEmpty()) {
                logger.info("Deleting " + userComments.size() + " comments by user " + user.getId());
                commentRepository.deleteAll(userComments);
            }
        } catch (Exception e) {
            logger.error("Error deleting user comments: " + e.getMessage());
            throw new RuntimeException("Failed to delete user comments", e);
        }
    }

    /**
     * Deletes all posts by the user and their associated comments.
     */
    private void deleteUserPosts(User user) {
        try {
            // Get all posts by user
            Page<Post> userPostsPage = postRepository.findByUser(user, Pageable.unpaged());
            List<Post> userPosts = userPostsPage.getContent();

            if (!userPosts.isEmpty()) {
                logger.info("Deleting " + userPosts.size() + " posts by user " + user.getId());

                // Delete comments for each post first
                for (Post post : userPosts) {
                    List<Comment> postComments = commentRepository.findByPost(post);
                    if (!postComments.isEmpty()) {
                        commentRepository.deleteAll(postComments);
                    }
                }

                // Delete the posts
                postRepository.deleteAll(userPosts);
            }
        } catch (Exception e) {
            logger.error("Error deleting user posts: " + e.getMessage());
            throw new RuntimeException("Failed to delete user posts", e);
        }
    }

    /**
     * Deletes all friendships where user is either requester or addressee.
     */
    private void deleteUserFriendships(User user) {
        try {
            List<Friendship> friendships = friendshipRepository.findByRequesterOrAddressee(user, user);
            if (!friendships.isEmpty()) {
                logger.info("Deleting " + friendships.size() + " friendships for user " + user.getId());
                friendshipRepository.deleteAll(friendships);
            }
        } catch (Exception e) {
            logger.error("Error deleting user friendships: " + e.getMessage());
            throw new RuntimeException("Failed to delete user friendships", e);
        }
    }

    /**
     * Hämtar en användare med alla inlägg.
     *
     * @param id ID för användaren.
     * @return {@link User}-entiteten med posts initialiserade, eller {@code null} om användaren inte finns.
     */
    @Transactional(readOnly = true)
    public User getUserWithPosts(Long id) {
        logger.info("Fetching user with posts, userId: " + id);

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            logger.warn("User with posts not found, userId: " + id);
            return null;
        }

        User user = userOptional.get();
        user.getPosts().size(); // initierar lazy-loaded posts
        return user;
    }

    /**
     * Autentiserar en användare baserat på användarnamn och lösenord.
     *
     * @param username    användarnamnet.
     * @param rawPassword rålösenordet som ska verifieras.
     * @return {@code true} om autentisering lyckades, annars {@code false}.
     */
    @Transactional(readOnly = true)
    public boolean authenticateUser(String username, String rawPassword) {
        logger.info("Login attempt for username: " + username);

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            logger.warn("Login failed – user not found: " + username);
            return false;
        }

        boolean matches = passwordEncoder.matches(rawPassword, userOptional.get().getPassword());

        if (matches) {
            logger.info("Login successfully for username: " + username);
        } else {
            logger.warn("Login failed – invalid password for username: " + username);
        }

        return matches;
    }

    /**
     * Uppdaterar en användares information baserat på {@link UserRegistrationDTO}.
     *
     * @param id        ID för användaren som ska uppdateras.
     * @param updateDTO DTO med uppdaterad användardata.
     * @return den uppdaterade {@link User}-entiteten.
     * @throws NoSuchElementException om användaren inte hittas.
     */
    @Transactional
    public User updateUser(Long id, UserRegistrationDTO updateDTO) {
        logger.info("Updating user with id: " + id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Update failed – user not found, userId: " + id);
                    return new NoSuchElementException("User not found");
                });

        user.setUsername(updateDTO.getUsername());
        user.setEmail(updateDTO.getEmail());

        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isBlank()) {
            logger.info("Updating password for userId: " + id);
            user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        user.setDisplayName(updateDTO.getDisplayName());
        user.setBio(updateDTO.getBio());

        User updatedUser = userRepository.save(user);
        logger.info("User updated successfully, userId: " + updatedUser.getId());

        return updatedUser;
    }
}
