package se.jensen.meiying.socialapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.meiying.socialapp.model.Comment;
import se.jensen.meiying.socialapp.model.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostOrderByCreatedAtAsc(Post post);
}
