package se.jensen.meiying.socialapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    List<Post> findByUserId(Long userId);

    List<Post> findByUserUsername(String username);

    @Query("SELECT p FROM Post p WHERE p.content LIKE %:keyword%")
    List<Post> searchByContent(@Param("keyword") String keyword);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByUserUsernameOrderByCreatedAtDesc(String username, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments ORDER BY p.createdAt DESC")
    List<Post> findAllWithComments();

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.id = :id")
    Post findByIdWithComments(@Param("id") Long id);
}