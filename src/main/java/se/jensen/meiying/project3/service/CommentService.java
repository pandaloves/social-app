package se.jensen.meiying.project3.service;

import se.jensen.meiying.project3.model.Comment;
import se.jensen.meiying.project3.model.Post;
import se.jensen.meiying.project3.model.User;
import se.jensen.meiying.project3.repository.CommentRepository;
import se.jensen.meiying.project3.repository.PostRepository;
import se.jensen.meiying.project3.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Comment createComment(Long postId, Long userId, String commentText) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setAuthor(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsForPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        return commentRepository.findByPostOrderByCreatedAtAsc(post);
    }
}