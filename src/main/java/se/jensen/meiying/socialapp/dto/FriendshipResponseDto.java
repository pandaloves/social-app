package se.jensen.meiying.socialapp.dto;

import se.jensen.meiying.socialapp.model.FriendshipStatus;

public class FriendshipResponseDto {

    private Long id;
    private UserInfoDTO requester;
    private UserInfoDTO addressee;
    private FriendshipStatus status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserInfoDTO getRequester() { return requester; }
    public void setRequester(UserInfoDTO requester) { this.requester = requester; }

    public UserInfoDTO getAddressee() { return addressee; }
    public void setAddressee(UserInfoDTO addressee) { this.addressee = addressee; }

    public FriendshipStatus getStatus() { return status; }
    public void setStatus(FriendshipStatus status) { this.status = status; }
}
