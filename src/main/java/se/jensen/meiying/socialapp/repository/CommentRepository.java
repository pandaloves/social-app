package se.jensen.meiying.socialapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.meiying.socialapp.model.Comment;
import se.jensen.meiying.socialapp.model.Post;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link Comment} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard database operations.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieves all comments for a given post, ordered by creation time in ascending order.
     *
     * @param post The post for which to retrieve comments.
     * @return A list of comments associated with the given post, sorted by creation timestamp.
     */
    List<Comment> findByPostOrderByCreatedAtAsc(Post post);
}
