package se.jensen.meiying.socialapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.meiying.socialapp.model.Friendship;
import se.jensen.meiying.socialapp.model.FriendshipStatus;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.repository.FriendshipRepository;
import se.jensen.meiying.socialapp.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * {@link FriendshipServiceTest} innehåller enhetstester för {@link FriendshipService}.
 * <p>
 * Klassen använder Mockito för att mocka beroenden och verifiera beteendet hos
 * {@link FriendshipService} utan att behöva ansluta till en faktisk databas.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FriendshipService friendshipService;

    /**
     * Testar att skapa en ny vänskapsförfrågan mellan två användare fungerar korrekt.
     * <p>
     * Metoden mockar både användarhämtning och sparande av {@link Friendship}-objekt,
     * och verifierar att statusen sätts till {@link FriendshipStatus#PENDING} samt
     * att requester och addressee är korrekt satta.
     * </p>
     */
    @Test
    void createFriendship_success() {
        User requester = new User();
        requester.setId(1L);

        User addressee = new User();
        addressee.setId(2L);

        // Mockar att användare hittas i repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(requester));
        when(userRepository.findById(2L)).thenReturn(Optional.of(addressee));

        // Mockar sparande av vänskapsobjekt
        when(friendshipRepository.save(any(Friendship.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Friendship friendship = friendshipService.createFriendship(1L, 2L);

        // Verifierar att vänskapens status och användare är korrekta
        assertEquals(FriendshipStatus.PENDING, friendship.getStatus());
        assertEquals(requester, friendship.getRequester());
        assertEquals(addressee, friendship.getAddressee());
    }
}
