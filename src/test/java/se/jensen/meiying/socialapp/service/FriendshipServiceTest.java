package se.jensen.meiying.socialapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.meiying.socialapp.model.Friendship;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.model.FriendshipStatus;
import se.jensen.meiying.socialapp.repository.FriendshipRepository;
import se.jensen.meiying.socialapp.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FriendshipService friendshipService;

    @Test
    void createFriendship_success() {
        User requester = new User();
        requester.setId(1L);

        User addressee = new User();
        addressee.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(requester));
        when(userRepository.findById(2L)).thenReturn(Optional.of(addressee));

        when(friendshipRepository.save(any(Friendship.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Friendship friendship = friendshipService.createFriendship(1L, 2L);

        assertEquals(FriendshipStatus.PENDING, friendship.getStatus());
        assertEquals(requester, friendship.getRequester());
        assertEquals(addressee, friendship.getAddressee());
    }
}
