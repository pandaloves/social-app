package se.jensen.meiying.project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.meiying.project3.model.Friendship;
import se.jensen.meiying.project3.model.User;
import se.jensen.meiying.project3.model.FriendshipStatus;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByRequesterOrAddressee(User requester, User addressee);

    List<Friendship> findByRequesterOrAddresseeAndStatus(
            User requester, User addressee, FriendshipStatus status);
}
