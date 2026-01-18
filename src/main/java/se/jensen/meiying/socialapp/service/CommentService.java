package se.jensen.meiying.socialapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.meiying.socialapp.model.Comment;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.repository.CommentRepository;
import se.jensen.meiying.socialapp.repository.PostRepository;
import se.jensen.meiying.socialapp.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * {@link CommentService} hanterar logik för att skapa och hämta kommentarer
 * kopplade till {@link Post} och {@link User}.
 * <p>
 * Service-klassen använder {@link CommentRepository}, {@link PostRepository} och {@link UserRepository}
 * för att interagera med databasen.
 * </p>
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * Skapar en ny instans av {@link CommentService}.
     *
     * @param commentRepository repository för att hantera {@link Comment}-entiteter.
     * @param postRepository    repository för att hantera {@link Post}-entiteter.
     * @param userRepository    repository för att hantera {@link User}-entiteter.
     */
    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * Skapar en ny kommentar för en specifik post av en given användare.
     * <p>
     * Metoden hämtar först {@link Post} och {@link User} från databasen baserat på deras ID.
     * Om posten eller användaren inte finns kastas ett {@link NoSuchElementException}.
     * Kommentaren sparas sedan i {@link CommentRepository}.
     * </p>
     *
     * @param postId      ID för posten som kommentaren ska kopplas till.
     * @param userId      ID för användaren som skapar kommentaren.
     * @param commentText texten för kommentaren.
     * @return den skapade {@link Comment}-entiteten.
     * @throws NoSuchElementException om posten eller användaren inte hittas.
     */
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

    /**
     * Hämtar alla kommentarer för en specifik post sorterade i stigande ordning efter skapelsedatum.
     * <p>
     * Om posten inte finns kastas ett {@link NoSuchElementException}.
     * </p>
     *
     * @param postId ID för posten vars kommentarer ska hämtas.
     * @return en lista med {@link Comment}-entiteter sorterade efter skapelsedatum.
     * @throws NoSuchElementException om posten inte hittas.
     */
    @Transactional(readOnly = true)
    public List<Comment> getCommentsForPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        return commentRepository.findByPostOrderByCreatedAtAsc(post);
    }
}
