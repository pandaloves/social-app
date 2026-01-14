package se.jensen.meiying.socialapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.meiying.socialapp.logging.AppLogger;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.dto.UserRegistrationDTO;
import se.jensen.meiying.socialapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppLogger logger;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AppLogger logger) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.logger = logger;
    }

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

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        logger.info("Fetching user by id: " + id);
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        logger.info("Fetching user by username: " + username);
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void deleteUser(Long id) {
        logger.info("Attempting to delete user with id: " + id);

        if (!userRepository.existsById(id)) {
            logger.warn("Delete failed – user not found with id: " + id);
            throw new NoSuchElementException("User not found");
        }

        userRepository.deleteById(id);
        logger.info("User deleted successfully with id: " + id);
    }

    @Transactional(readOnly = true)
    public User getUserWithPosts(Long id) {
        logger.info("Fetching user with posts, userId: " + id);

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            logger.warn("User with posts not found, userId: " + id);
            return null;
        }

        User user = userOptional.get();
        user.getPosts().size();
        return user;
    }

    @Transactional
    public void deleteUserWithAllPosts(Long id) {
        logger.info("Deleting user and all posts, userId: " + id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Delete with posts failed – user not found, userId: " + id);
                    return new NoSuchElementException("User not found");
                });

        userRepository.delete(user);
        logger.info("User and all posts deleted, userId: " + id);
    }

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
            logger.info("Login successful for username: " + username);
        } else {
            logger.warn("Login failed – invalid password for username: " + username);
        }

        return matches;
    }

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
