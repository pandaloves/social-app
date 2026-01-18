package se.jensen.meiying.socialapp.dto;

import se.jensen.meiying.socialapp.model.FriendshipStatus;

/**
 * Data Transfer Object used for sending friendship information
 * back to the client.
 * <p>
 * This DTO contains details about a friendship relation including
 * involved users and the current friendship status.
 */
public class FriendshipResponseDto {

    /**
     * Unique identifier of the friendship.
     */
    private Long id;

    /**
     * User who sent the friendship request.
     */
    private UserInfoDTO requester;

    /**
     * User who received the friendship request.
     */
    private UserInfoDTO addressee;

    /**
     * Current status of the friendship (PENDING, ACCEPTED, REJECTED).
     */
    private FriendshipStatus status;

    /**
     * Returns the friendship ID.
     *
     * @return friendship ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the friendship ID.
     *
     * @param id the friendship ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user who initiated the friendship request.
     *
     * @return requester user information
     */
    public UserInfoDTO getRequester() {
        return requester;
    }

    /**
     * Sets the user who initiated the friendship request.
     *
     * @param requester requester user information
     */
    public void setRequester(UserInfoDTO requester) {
        this.requester = requester;
    }

    /**
     * Returns the user who received the friendship request.
     *
     * @return addressee user information
     */
    public UserInfoDTO getAddressee() {
        return addressee;
    }

    /**
     * Sets the user who received the friendship request.
     *
     * @param addressee addressee user information
     */
    public void setAddressee(UserInfoDTO addressee) {
        this.addressee = addressee;
    }

    /**
     * Returns the current friendship status.
     *
     * @return friendship status
     */
    public FriendshipStatus getStatus() {
        return status;
    }

    /**
     * Sets the current friendship status.
     *
     * @param status the friendship status
     */
    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
