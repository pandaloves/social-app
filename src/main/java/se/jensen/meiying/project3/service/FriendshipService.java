package se.jensen.meiying.project3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.meiying.project3.model.*;
import se.jensen.meiying.project3.repository.FriendshipRepository;
import se.jensen.meiying.project3.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipService(FriendshipRepository friendshipRepository,
                             UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Friendship createFriendship(Long requesterId, Long addresseeId) {

        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new NoSuchElementException("Requester not found"));

        User addressee = userRepository.findById(addresseeId)
                .orElseThrow(() -> new NoSuchElementException("Addressee not found"));

        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setAddressee(addressee);
        friendship.setStatus(FriendshipStatus.PENDING);

        return friendshipRepository.save(friendship);
    }

    @Transactional(readOnly = true)
    public List<Friendship> getFriendshipsForUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return friendshipRepository.findByRequesterOrAddressee(user, user);
    }

    @Transactional
    public Friendship acceptFriendship(Long id) {
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Friendship not found"));

        friendship.setStatus(FriendshipStatus.ACCEPTED);
        return friendshipRepository.save(friendship);
    }

    @Transactional
    public Friendship rejectFriendship(Long id) {
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Friendship not found"));

        friendship.setStatus(FriendshipStatus.REJECTED);
        return friendshipRepository.save(friendship);
    }

    @Transactional(readOnly = true)
    public List<User> getAcceptedFriends(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return friendshipRepository
                .findByRequesterOrAddresseeAndStatus(user, user, FriendshipStatus.ACCEPTED)
                .stream()
                .map(f -> f.getRequester().equals(user)
                        ? f.getAddressee()
                        : f.getRequester())
                .collect(Collectors.toList());
    }
}
