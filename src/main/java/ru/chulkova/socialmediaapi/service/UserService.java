package ru.chulkova.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chulkova.socialmediaapi.dto.ProfileDto;
import ru.chulkova.socialmediaapi.dto.UserDto;
import ru.chulkova.socialmediaapi.exception.UserNotFoundException;
import ru.chulkova.socialmediaapi.mapper.UserMapper;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<ProfileDto> getAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::getProfile)
                .collect(Collectors.toList());
    }

    public void follow(Long followerId, Long userId) {
        assert !Objects.equals(followerId, userId);
        User updated = repository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        User follower = repository.findById(followerId)
                .orElseThrow(UserNotFoundException::new);
        updated.getSubscribers().add(follower);
        updated.getRequests().add(follower);
        repository.save(updated);
    }

    public void accept(Long followerId, Long toFollowId) {
        assert !Objects.equals(followerId, toFollowId);
        User userInfo = repository.findById(toFollowId)
                .orElseThrow(UserNotFoundException::new);
        User followerInfo = repository.findById(followerId)
                .orElseThrow(UserNotFoundException::new);
        userInfo.getFriends().add(followerInfo);
        userInfo.getRequests().remove(followerInfo);
        followerInfo.getFriends().add(userInfo);
        followerInfo.getSubscribers().add(userInfo);
        repository.save(userInfo);
        repository.save(followerInfo);
    }

    public void deny(Long followerId, Long userId) {
        assert !Objects.equals(followerId, userId);
        User userInfo = repository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        User follower = repository.findById(followerId)
                .orElseThrow(UserNotFoundException::new);
        userInfo.getRequests().remove(follower);
        repository.save(userInfo);
    }

    public List<UserDto> getRequests(User user) {
        return repository.findAllRequestsByUserId(user.id())
                .stream()
                .map(UserMapper::getTo)
                .collect(Collectors.toList());
    }
}