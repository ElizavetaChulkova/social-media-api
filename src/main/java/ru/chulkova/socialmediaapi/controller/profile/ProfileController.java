package ru.chulkova.socialmediaapi.controller.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.socialmediaapi.dto.ProfileDto;
import ru.chulkova.socialmediaapi.dto.UserDto;
import ru.chulkova.socialmediaapi.mapper.UserMapper;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.repository.UserRepository;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserRepository repository;

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable("userId") Long userId) {
        log.info("get user profile info");
        return ResponseEntity.ok(UserMapper.getProfile(repository.findById(userId).orElseThrow()));
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateProfile(@PathVariable("userId") Long userId, @RequestBody UserDto userDto) {
        log.info("update user profile {}", userId);
        User toUpdate = repository.findById(userId).orElseThrow();
        toUpdate.setName(userDto.getName());
        repository.save(toUpdate);
        return ResponseEntity.ok(UserMapper.getTo(repository.findById(userId).orElseThrow()));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) {
        log.info("delete user profile");
        User user = repository.findById(userId).orElseThrow();
        repository.delete(user);
    }
}