package se.jensen.meiying.socialapp.model;

/**
 * Enum representing the status of a friendship between two users.
 * <p>
 * - {@code PENDING}: The friendship request has been sent but not yet accepted or rejected.
 * - {@code ACCEPTED}: The friendship request has been accepted by the addressee.
 * - {@code REJECTED}: The friendship request has been rejected by the addressee.
 */
public enum FriendshipStatus {
    /**
     * Friendship request is pending and awaiting a response.
     */
    PENDING,

    /**
     * Friendship request has been accepted.
     */
    ACCEPTED,

    /**
     * Friendship request has been rejected.
     */
    REJECTED
}
