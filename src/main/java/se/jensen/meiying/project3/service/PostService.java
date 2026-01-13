package se.jensen.meiying.project3.service;

import se.jensen.meiying.project3.model.Post;
import se.jensen.meiying.project3.model.User;
import se.jensen.meiying.project3.repository.PostRepository;
import se.jensen.meiying.project3.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findByIdWithComments(id);
    }

    @Transactional(readOnly = true)
    public List<Post> searchPostsByContent(String keyword) {
        return postRepository.searchByContent(keyword);
    }

    @Transactional
    public Post createPost(Long userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Post post = new Post(content, user);
        user.addPost(post);

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePostContent(Long postId, String newContent) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        post.setContent(newContent);
        return postRepository.save(post);
    }

    @Transactional
    public boolean deletePost(Long postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Page<Post> getFeed(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Post> getUserWall(String username, Pageable pageable) {
        return postRepository.findByUserUsernameOrderByCreatedAtDesc(username, pageable);
    }
}