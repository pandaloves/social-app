package se.jensen.meiying.socialapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.meiying.socialapp.model.Friendship;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.model.FriendshipStatus;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByRequesterOrAddressee(User requester, User addressee);

    List<Friendship> findByRequesterOrAddresseeAndStatus(
            User requester, User addressee, FriendshipStatus status);
}
