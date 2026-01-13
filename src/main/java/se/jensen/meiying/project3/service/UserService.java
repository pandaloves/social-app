package se.jensen.meiying.project3.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.meiying.project3.model.User;
import se.jensen.meiying.project3.dto.UserRegistrationDTO;
import se.jensen.meiying.project3.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(UserRegistrationDTO registrationDTO) {

        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());

        String hashedPassword =
                passwordEncoder.encode(registrationDTO.getPassword());
        user.setPassword(hashedPassword);

        user.setDisplayName(registrationDTO.getDisplayName());
        user.setBio(registrationDTO.getBio());
        user.setRole("USER");

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User updateDisplayName(Long id, String displayName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setDisplayName(displayName);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    @Transactional(readOnly = true)
    public User getUserWithPosts(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.getPosts().size();
            return user;
        }
        return null;
    }

    @Transactional
    public void deleteUserWithAllPosts(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public boolean authenticateUser(String username, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) return false;

        User user = userOptional.get();

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }


    @Transactional
    public User updateUser(Long id, UserRegistrationDTO updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!user.getUsername().equals(updateDTO.getUsername())) {
            Optional<User> existingUserByUsername = userRepository.findByUsername(updateDTO.getUsername());
            if (existingUserByUsername.isPresent()) {
                throw new IllegalArgumentException("Username already exists");
            }
        }

        if (!user.getEmail().equals(updateDTO.getEmail())) {
            Optional<User> existingUserByEmail = userRepository.findByEmail(updateDTO.getEmail());
            if (existingUserByEmail.isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }
        }

        user.setUsername(updateDTO.getUsername());
        user.setEmail(updateDTO.getEmail());

        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        user.setDisplayName(updateDTO.getDisplayName());
        user.setBio(updateDTO.getBio());

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User createAdmin(UserRegistrationDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setDisplayName(dto.getDisplayName());
        user.setBio(dto.getBio());
        user.setRole("ADMIN");
        return userRepository.save(user);
    }

}