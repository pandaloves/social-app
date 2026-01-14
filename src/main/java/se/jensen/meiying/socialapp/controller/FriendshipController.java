package se.jensen.meiying.socialapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.meiying.socialapp.dto.*;
import se.jensen.meiying.socialapp.service.FriendshipService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

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

    @GetMapping("/{userId}")
    public ResponseEntity<List<FriendshipResponseDto>> getFriendships(
            @PathVariable Long userId) {

        var friendships = friendshipService.getFriendshipsForUser(userId)
                .stream()
                .map(DTOMapper::toFriendshipResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(friendships);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<FriendshipResponseDto> acceptFriendship(@PathVariable Long id) {
        var friendship = friendshipService.acceptFriendship(id);
        return ResponseEntity.ok(DTOMapper.toFriendshipResponseDto(friendship));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<FriendshipResponseDto> rejectFriendship(@PathVariable Long id) {
        var friendship = friendshipService.rejectFriendship(id);
        return ResponseEntity.ok(DTOMapper.toFriendshipResponseDto(friendship));
    }
}
