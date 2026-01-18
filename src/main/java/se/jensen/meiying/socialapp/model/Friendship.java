package se.jensen.meiying.socialapp.model;

import jakarta.persistence.*;

/**
 * Represents a friendship relationship between two users.
 * <p>
 * Each friendship has a requester (the user who sends the request) and
 * an addressee (the user who receives the request). The status indicates
 * whether the friendship is pending, accepted, or rejected.
 */
@Entity
public class Friendship {

    /**
     * The unique identifier for the friendship.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who initiated the friendship request.
     */
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    /**
     * The user who received the friendship request.
     */
    @ManyToOne
    @JoinColumn(name = "addressee_id", nullable = false)
    private User addressee;

    /**
     * The current status of the friendship.
     */
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    /**
     * @return the unique identifier of the friendship
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the user who requested the friendship
     */
    public User getRequester() {
        return requester;
    }

    /**
     * @param requester the user who requested the friendship
     */
    public void setRequester(User requester) {
        this.requester = requester;
    }

    /**
     * @return the user who received the friendship request
     */
    public User getAddressee() {
        return addressee;
    }

    /**
     * @param addressee the user who received the friendship request
     */
    public void setAddressee(User addressee) {
        this.addressee = addressee;
    }

    /**
     * @return the current status of the friendship
     */
    public FriendshipStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set for the friendship
     */
    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
