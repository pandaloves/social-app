package se.jensen.meiying.socialapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.meiying.socialapp.logging.AppLogger;
import se.jensen.meiying.socialapp.model.Post;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.repository.PostRepository;
import se.jensen.meiying.socialapp.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * {@link PostServiceTest} innehåller enhetstester för {@link PostService}.
 * <p>
 * Klassen använder Mockito för att mocka beroenden och verifiera beteendet hos
 * {@link PostService} utan att behöva ansluta till en riktig databas.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppLogger logger;

    @InjectMocks
    private PostService postService;

    /**
     * Testar skapandet av ett nytt inlägg för en användare.
     * <p>
     * Metoden mockar användarhämtning via {@link UserRepository} och verifierar att
     * det skapade {@link Post}-objektet får korrekt innehåll och associerad användare.
     * </p>
     */
    @Test
    void createPost_success() {
        User user = new User();
        user.setId(1L);

        // Mockar att användaren finns
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Skapar post
        Post post = postService.createPost(1L, "Hello World");

        // Verifierar postens innehåll och användare
        assertEquals("Hello World", post.getContent());
        assertEquals(user, post.getUser());
    }
}
