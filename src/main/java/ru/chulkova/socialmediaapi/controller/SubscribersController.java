package ru.chulkova.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.socialmediaapi.dto.UserDto;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class SubscribersController {

    private final UserService service;

    @GetMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getRequests(@AuthenticationPrincipal User user) {
        log.info("get requests");
        return service.getRequests(user);
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<String> follow(@AuthenticationPrincipal User user,
                                         @PathVariable("userId") Long userToFollowId) {
        log.info("follow {}", userToFollowId);
        service.follow(user.id(), userToFollowId);
        return ResponseEntity.ok("You followed user " + userToFollowId);
    }

    @PostMapping("/request/accept/{userId}")
    public ResponseEntity<String> accept(@AuthenticationPrincipal User user,
                                         @PathVariable("userId") Long subscriberId) {
        log.info("accept friend request by user {}", subscriberId);
        service.accept(subscriberId, user.id());
        return ResponseEntity.ok("You get a new friend");
    }

    @PostMapping("/request/deny/{userId}")
    public ResponseEntity<String> deny(@AuthenticationPrincipal User user,
                                       @PathVariable("userId") Long userToFollowId) {
        log.info("deny friend request by {}", userToFollowId);
        service.deny(userToFollowId, user.id());
        return ResponseEntity.ok("You denied friend request by user " + userToFollowId);
    }
}