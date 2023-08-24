package ru.chulkova.socialmediaapi.controller.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.socialmediaapi.dto.ProfileDto;
import ru.chulkova.socialmediaapi.dto.UserDto;
import ru.chulkova.socialmediaapi.exception.UserNotFoundException;
import ru.chulkova.socialmediaapi.mapper.UserMapper;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.repository.UserRepository;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserRepository repository;

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(@AuthenticationPrincipal User user) {
        log.info("get user profile info");
        return ResponseEntity.ok(UserMapper.getProfile(repository.findById(user.id())
                .orElseThrow(UserNotFoundException::new)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateProfile(@AuthenticationPrincipal User user,
                                                 @RequestBody UserDto userDto) {
        log.info("update user profile {}", user.id());
        User toUpdate = repository.findById(user.id()).orElseThrow();
        toUpdate.setName(userDto.getName());
        repository.save(toUpdate);
        return ResponseEntity.ok(UserMapper.getTo(repository.findById(user.id())
                .orElseThrow(UserNotFoundException::new)));
    }

    @DeleteMapping
    public void delete(@AuthenticationPrincipal User user) {
        log.info("delete user profile");
        repository.delete(user);
    }
}