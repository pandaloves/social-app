package se.jensen.meiying.project3.dto;

public class FriendshipRequestDto {

    private Long requesterUserId;
    private Long addresseeUserId;

    public Long getRequesterUserId() { return requesterUserId; }
    public void setRequesterUserId(Long requesterUserId) { this.requesterUserId = requesterUserId; }

    public Long getAddresseeUserId() { return addresseeUserId; }
    public void setAddresseeUserId(Long addresseeUserId) { this.addresseeUserId = addresseeUserId; }
}
