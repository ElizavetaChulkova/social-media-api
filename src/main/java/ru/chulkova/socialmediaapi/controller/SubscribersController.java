package ru.chulkova.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.socialmediaapi.dto.UserDto;
import ru.chulkova.socialmediaapi.mapper.UserMapper;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.repository.UserRepository;
import ru.chulkova.socialmediaapi.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class SubscribersController {

    private final UserService service;
    private final UserRepository repository;

    @GetMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getRequests(@AuthenticationPrincipal User user) {
        log.info("get requests");
        return repository.findAllRequestsByUserId(user.id())
                .stream()
                .map(UserMapper::getTo)
                .collect(Collectors.toList());
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<String> follow(@AuthenticationPrincipal User user,
                                         @PathVariable("userId") Long userToFollowId) {
        log.info("follow {}", userToFollowId);
        assert userToFollowId != user.id();
        service.follow(user.id(), userToFollowId);
        User toFollow = repository.findById(userToFollowId).orElseThrow();
        return ResponseEntity.ok("You followed user " + toFollow.getName());
    }

    @PostMapping("/request/accept/{userId}")
    public ResponseEntity<String> accept(@AuthenticationPrincipal User user,
                                         @PathVariable("userId") Long subscriberId) {
        User newFriend = repository.findById(subscriberId).get();
        log.info("accept friend request by {}", newFriend.getEmail());
        assert subscriberId != user.id();
        service.accept(subscriberId, user.id());
        return ResponseEntity.ok("You get a new friend " + newFriend.getName());
    }

    @PostMapping("/request/deny/{userId}")
    public ResponseEntity<String> deny(@AuthenticationPrincipal User user,
                                       @PathVariable("userId") Long userToFollowId) {
        User requestOwner = repository.findById(userToFollowId).get();
        log.info("deny friend request by {}", requestOwner.getEmail());
        assert userToFollowId != user.id();
        service.deny(userToFollowId, user.id());
        return ResponseEntity.ok("You denied friend request by user " + requestOwner.getName());
    }
}