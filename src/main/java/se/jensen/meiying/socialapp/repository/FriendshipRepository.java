package se.jensen.meiying.socialapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.jensen.meiying.socialapp.model.Friendship;
import se.jensen.meiying.socialapp.model.FriendshipStatus;
import se.jensen.meiying.socialapp.model.User;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link Friendship} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard database operations.
 */
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    /**
     * Retrieves all friendships where the specified user is either the requester or the addressee.
     *
     * @param requester The user who may have requested the friendship.
     * @param addressee The user who may have received the friendship request.
     * @return A list of friendships where the given user is involved as requester or addressee.
     */
    List<Friendship> findByRequesterOrAddressee(User requester, User addressee);

    @Modifying
    @Query("DELETE FROM Friendship f WHERE f.requester.id = :userId OR f.addressee.id = :userId")
    void deleteFriendshipsByUserId(@Param("userId") Long userId);

    /**
     * Retrieves all friendships where the specified user is either the requester or the addressee
     * and the friendship has a specific status.
     *
     * @param requester The user who may have requested the friendship.
     * @param addressee The user who may have received the friendship request.
     * @param status    The status of the friendship (e.g., PENDING, ACCEPTED, REJECTED).
     * @return A list of friendships matching the user involvement and specified status.
     */
    List<Friendship> findByRequesterOrAddresseeAndStatus(
            User requester, User addressee, FriendshipStatus status);
}
