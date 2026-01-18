package se.jensen.meiying.socialapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.meiying.socialapp.dto.DTOMapper;
import se.jensen.meiying.socialapp.dto.FriendshipRequestDto;
import se.jensen.meiying.socialapp.dto.FriendshipResponseDto;
import se.jensen.meiying.socialapp.service.FriendshipService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing friendships between users.
 * Provides endpoints to create, retrieve, accept, and reject friendships.
 */
@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    /**
     * Constructor to inject the FriendshipService.
     *
     * @param friendshipService the service that handles friendship operations
     */
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    /**
     * Creates a new friendship request between two users.
     *
     * @param dto the FriendshipRequestDto containing requester and addressee user IDs
     * @return the created friendship wrapped in a ResponseEntity
     */
    @PostMapping
    public ResponseEntity<FriendshipResponseDto> createFriendship(
            @RequestBody FriendshipRequestDto dto) {

        var friendship = friendshipService.createFriendship(
                dto.getRequesterUserId(),
                dto.getAddresseeUserId()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DTOMapper.toFriendshipResponseDto(friendship));
    }

    /**
     * Retrieves all friendships associated with a given user.
     *
     * @param userId the ID of the user
     * @return a list of FriendshipResponseDto wrapped in a ResponseEntity
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<FriendshipResponseDto>> getFriendships(
            @PathVariable Long userId) {

        var friendships = friendshipService.getFriendshipsForUser(userId)
                .stream()
                .map(DTOMapper::toFriendshipResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(friendships);
    }

    /**
     * Accepts a pending friendship request.
     *
     * @param id the ID of the friendship to accept
     * @return the updated FriendshipResponseDto wrapped in a ResponseEntity
     */
    @PutMapping("/{id}/accept")
    public ResponseEntity<FriendshipResponseDto> acceptFriendship(@PathVariable Long id) {
        var friendship = friendshipService.acceptFriendship(id);
        return ResponseEntity.ok(DTOMapper.toFriendshipResponseDto(friendship));
    }

    /**
     * Rejects a pending friendship request.
     *
     * @param id the ID of the friendship to reject
     * @return the updated FriendshipResponseDto wrapped in a ResponseEntity
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<FriendshipResponseDto> rejectFriendship(@PathVariable Long id) {
        var friendship = friendshipService.rejectFriendship(id);
        return ResponseEntity.ok(DTOMapper.toFriendshipResponseDto(friendship));
    }
}
