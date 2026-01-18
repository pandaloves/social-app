package se.jensen.meiying.socialapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.jensen.meiying.socialapp.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link User} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard database operations and includes
 * additional query methods for searching users by username, email, role, display name, and bio.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user.
     * @return An {@link Optional} containing the user if found, or empty if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email address.
     *
     * @param email The email of the user.
     * @return An {@link Optional} containing the user if found, or empty if not found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user exists with the specified username.
     *
     * @param username The username to check.
     * @return True if a user with the username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user exists with the specified email address.
     *
     * @param email The email to check.
     * @return True if a user with the email exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Finds all users with the specified role.
     *
     * @param role The role to filter users by.
     * @return A list of users who have the specified role.
     */
    List<User> findByRole(String role);

    /**
     * Finds all users whose display name contains the specified string (case-sensitive).
     *
     * @param name The string to search for in display names.
     * @return A list of users whose display name contains the given string.
     */
    List<User> findByDisplayNameContaining(String name);

    /**
     * Finds all users whose bio contains the specified keyword (case-sensitive).
     *
     * @param keyword The keyword to search for in bios.
     * @return A list of users whose bio contains the given keyword.
     */
    List<User> findByBioContaining(String keyword);

    /**
     * Counts the number of users with the specified role.
     *
     * @param role The role to filter users by.
     * @return The number of users who have the specified role.
     */
    long countByRole(String role);
}
