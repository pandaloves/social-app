package se.jensen.meiying.socialapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link Post} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard database operations and includes
 * custom queries for searching and fetching posts with comments.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Retrieves a paginated list of posts created by a user with the specified ID.
     *
     * @param userId   The ID of the user whose posts are to be retrieved.
     * @param pageable The pagination information.
     * @return A page of posts created by the specified user.
     */
    Page<Post> findByUserId(Long userId, Pageable pageable);

    /**
     * Retrieves a post by its ID along with its associated comments.
     *
     * @param id The ID of the post.
     * @return The post with its comments loaded, or null if not found.
     */
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.id = :id")
    Post findByIdWithComments(@Param("id") Long id);

    /**
     * Searches for posts that contain the specified keyword in their content, case-insensitive.
     *
     * @param keyword The keyword to search for in post content.
     * @return A list of posts whose content contains the keyword.
     */
    @Query("SELECT p FROM Post p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Post> searchByContent(@Param("keyword") String keyword);

    /**
     * Retrieves all posts sorted by creation date in descending order (most recent first).
     *
     * @return A list of all posts ordered by creation date descending.
     */
    List<Post> findAllByOrderByCreatedAtDesc();

    /**
     * Retrieves all posts in a paginated fashion, ordered by creation date descending.
     *
     * @param pageable The pagination information.
     * @return A page of posts ordered by creation date descending.
     */
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Retrieves a paginated list of posts authored by a specific {@link User}.
     *
     * @param user     The user whose posts are to be retrieved.
     * @param pageable The pagination information.
     * @return A page of posts authored by the specified user.
     */
    Page<Post> findByUser(User user, Pageable pageable);

    /**
     * Retrieves a paginated list of posts authored by a user with the specified username,
     * ordered by creation date descending.
     *
     * @param username The username of the user whose posts are to be retrieved.
     * @param pageable The pagination information.
     * @return A page of posts authored by the user with the given username.
     */
    Page<Post> findByUserUsernameOrderByCreatedAtDesc(String username, Pageable pageable);
}
