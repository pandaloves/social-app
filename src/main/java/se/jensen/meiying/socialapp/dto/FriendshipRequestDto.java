package se.jensen.meiying.socialapp.dto;

/**
 * Data Transfer Object used when creating a new friendship request.
 * <p>
 * This DTO contains the IDs of the user sending the request
 * and the user receiving the request.
 */
public class FriendshipRequestDto {

    /**
     * ID of the user who sends the friendship request.
     */
    private Long requesterUserId;

    /**
     * ID of the user who receives the friendship request.
     */
    private Long addresseeUserId;

    /**
     * Returns the ID of the requesting user.
     *
     * @return requester user ID
     */
    public Long getRequesterUserId() {
        return requesterUserId;
    }

    /**
     * Sets the ID of the requesting user.
     *
     * @param requesterUserId the user ID of the requester
     */
    public void setRequesterUserId(Long requesterUserId) {
        this.requesterUserId = requesterUserId;
    }

    /**
     * Returns the ID of the receiving user.
     *
     * @return addressee user ID
     */
    public Long getAddresseeUserId() {
        return addresseeUserId;
    }

    /**
     * Sets the ID of the receiving user.
     *
     * @param addresseeUserId the user ID of the addressee
     */
    public void setAddresseeUserId(Long addresseeUserId) {
        this.addresseeUserId = addresseeUserId;
    }
}
