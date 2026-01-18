package se.jensen.meiying.socialapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.meiying.socialapp.model.Friendship;
import se.jensen.meiying.socialapp.model.FriendshipStatus;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.repository.FriendshipRepository;
import se.jensen.meiying.socialapp.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * {@link FriendshipService} hanterar logik för vänskapsrelationer mellan {@link User}-entiteter.
 * <p>
 * Service-klassen hanterar skapande, accepterande, avslag och hämtning av vänskapsrelationer,
 * samt listar accepterade vänner för en användare.
 * </p>
 */
@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    /**
     * Skapar en ny instans av {@link FriendshipService}.
     *
     * @param friendshipRepository repository för att hantera {@link Friendship}-entiteter.
     * @param userRepository       repository för att hantera {@link User}-entiteter.
     */
    public FriendshipService(FriendshipRepository friendshipRepository,
                             UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    /**
     * Skapar en ny vänskapsförfrågan mellan två användare.
     * <p>
     * Status sätts initialt till {@link FriendshipStatus#PENDING}.
     * </p>
     *
     * @param requesterId ID för användaren som skickar vänskapsförfrågan.
     * @param addresseeId ID för användaren som mottar vänskapsförfrågan.
     * @return den skapade {@link Friendship}-entiteten.
     * @throws NoSuchElementException om någon av användarna inte hittas.
     */
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

    /**
     * Hämtar alla vänskapsrelationer för en specifik användare.
     * <p>
     * Returnerar relationer där användaren är antingen requester eller addressee.
     * </p>
     *
     * @param userId ID för användaren vars vänskapsrelationer ska hämtas.
     * @return en lista med {@link Friendship}-entiteter.
     * @throws NoSuchElementException om användaren inte hittas.
     */
    @Transactional(readOnly = true)
    public List<Friendship> getFriendshipsForUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return friendshipRepository.findByRequesterOrAddressee(user, user);
    }

    /**
     * Accepterar en vänskapsförfrågan.
     * <p>
     * Status ändras till {@link FriendshipStatus#ACCEPTED}.
     * </p>
     *
     * @param id ID för vänskapsförfrågan som ska accepteras.
     * @return den uppdaterade {@link Friendship}-entiteten.
     * @throws NoSuchElementException om vänskapsförfrågan inte hittas.
     */
    @Transactional
    public Friendship acceptFriendship(Long id) {
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Friendship not found"));

        friendship.setStatus(FriendshipStatus.ACCEPTED);
        return friendshipRepository.save(friendship);
    }

    /**
     * Avslår en vänskapsförfrågan.
     * <p>
     * Status ändras till {@link FriendshipStatus#REJECTED}.
     * </p>
     *
     * @param id ID för vänskapsförfrågan som ska avslås.
     * @return den uppdaterade {@link Friendship}-entiteten.
     * @throws NoSuchElementException om vänskapsförfrågan inte hittas.
     */
    @Transactional
    public Friendship rejectFriendship(Long id) {
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Friendship not found"));

        friendship.setStatus(FriendshipStatus.REJECTED);
        return friendshipRepository.save(friendship);
    }

    /**
     * Hämtar alla accepterade vänner för en specifik användare.
     * <p>
     * Returnerar en lista med {@link User}-entiteter som har accepterad vänskap med användaren.
     * </p>
     *
     * @param userId ID för användaren vars accepterade vänner ska hämtas.
     * @return en lista med {@link User}-entiteter som är accepterade vänner.
     * @throws NoSuchElementException om användaren inte hittas.
     */
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
