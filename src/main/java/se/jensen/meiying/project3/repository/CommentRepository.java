package se.jensen.meiying.project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.meiying.project3.model.Comment;
import se.jensen.meiying.project3.model.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostOrderByCreatedAtAsc(Post post);
}
