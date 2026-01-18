package se.jensen.meiying.socialapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.meiying.socialapp.logging.AppLogger;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.repository.PostRepository;
import se.jensen.meiying.socialapp.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * {@link PostService} hanterar logik för {@link Post}-entiteter.
 * <p>
 * Service-klassen hanterar skapande, uppdatering, hämtning och borttagning av inlägg,
 * samt sökning och visning av användares vägg och feed.
 * </p>
 */
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AppLogger logger;

    /**
     * Skapar en ny instans av {@link PostService}.
     *
     * @param postRepository repository för att hantera {@link Post}-entiteter.
     * @param userRepository repository för att hantera {@link User}-entiteter.
     * @param logger         logger för applikationsloggning.
     */
    public PostService(PostRepository postRepository, UserRepository userRepository, AppLogger logger) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.logger = logger;
    }

    /**
     * Hämtar ett inlägg baserat på dess ID, inklusive kommentarer.
     *
     * @param id ID för inlägget.
     * @return den hittade {@link Post}-entiteten.
     * @throws NoSuchElementException om inlägget inte hittas.
     */
    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        Post post = postRepository.findByIdWithComments(id);
        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }
        logger.info("Fetched post.");
        return post;
    }

    /**
     * Hämtar alla inlägg med pagination.
     *
     * @param pageable pagination-information.
     * @return en sida med {@link Post}-entiteter.
     */
    @Transactional(readOnly = true)
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /**
     * Hämtar alla inlägg av en specifik användare med pagination.
     *
     * @param userId   ID för användaren.
     * @param pageable pagination-information.
     * @return en sida med {@link Post}-entiteter.
     * @throws NoSuchElementException om användaren inte hittas.
     */
    @Transactional(readOnly = true)
    public Page<Post> getPostsByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return postRepository.findByUser(user, pageable);
    }

    /**
     * Hämtar alla inlägg sorterade i fallande ordning efter skapelsedatum.
     *
     * @return en lista med {@link Post}-entiteter.
     */
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Söker efter inlägg som innehåller ett specifikt nyckelord.
     *
     * @param keyword sökordet.
     * @return en lista med {@link Post}-entiteter som matchar sökningen.
     */
    @Transactional(readOnly = true)
    public List<Post> searchPostsByContent(String keyword) {
        return postRepository.searchByContent(keyword);
    }

    /**
     * Skapar ett nytt inlägg för en specifik användare.
     *
     * @param userId  ID för användaren som skapar inlägget.
     * @param content textinnehållet för inlägget.
     * @return den skapade {@link Post}-entiteten.
     * @throws NoSuchElementException om användaren inte hittas.
     */
    @Transactional
    public Post createPost(Long userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Post post = new Post(content, user);
        user.addPost(post);

        return postRepository.save(post);
    }

    /**
     * Uppdaterar innehållet i ett befintligt inlägg.
     *
     * @param postId     ID för inlägget som ska uppdateras.
     * @param newContent det nya innehållet.
     * @return den uppdaterade {@link Post}-entiteten.
     * @throws NoSuchElementException om inlägget inte hittas.
     */
    @Transactional
    public Post updatePostContent(Long postId, String newContent) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        post.setContent(newContent);
        return postRepository.save(post);
    }

    /**
     * Tar bort ett inlägg baserat på dess ID.
     *
     * @param postId ID för inlägget som ska tas bort.
     * @return {@code true} om inlägget fanns och raderades, annars {@code false}.
     */
    @Transactional
    public boolean deletePost(Long postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }

    /**
     * Hämtar feeden med alla inlägg sorterade i fallande ordning med pagination.
     *
     * @param pageable pagination-information.
     * @return en sida med {@link Post}-entiteter.
     */
    @Transactional(readOnly = true)
    public Page<Post> getFeed(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    /**
     * Hämtar en användares vägg med inlägg sorterade i fallande ordning.
     *
     * @param username användarnamnet.
     * @param pageable pagination-information.
     * @return en sida med {@link Post}-entiteter.
     */
    @Transactional(readOnly = true)
    public Page<Post> getUserWall(String username, Pageable pageable) {
        return postRepository.findByUserUsernameOrderByCreatedAtDesc(username, pageable);
    }
}
