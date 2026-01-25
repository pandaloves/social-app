package se.jensen.meiying.socialapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.jensen.meiying.socialapp.model.Comment;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;

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

    List<Comment> findByAuthor(User author);

    List<Comment> findByPost(Post post);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.author.id = :userId")
    void deleteCommentsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.post.user.id = :userId")
    void deleteCommentsOnUserPosts(@Param("userId") Long userId);
}
