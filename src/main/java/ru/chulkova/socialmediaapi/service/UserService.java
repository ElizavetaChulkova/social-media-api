package ru.chulkova.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void follow(Long followerId, Long userId) {
        User updated = repository.findById(userId).orElseThrow();
        User follower = repository.findById(followerId).orElseThrow();
        updated.getSubscribers().add(follower);
        updated.getRequests().add(follower);
        repository.save(updated);
    }

    public void accept(Long followerId, Long toFollowId) {
        User userInfo = repository.findById(toFollowId).orElseThrow();
        User followerInfo = repository.findById(followerId).orElseThrow();
        userInfo.getFriends().add(followerInfo);
        userInfo.getRequests().remove(followerInfo);
        followerInfo.getFriends().add(userInfo);
        followerInfo.getSubscribers().add(userInfo);
        repository.save(userInfo);
        repository.save(followerInfo);
    }

    public void deny(Long followerId, Long userId) {
        User userInfo = repository.findById(userId).orElseThrow();
        User follower = repository.findById(followerId).orElseThrow();
        userInfo.getRequests().remove(follower);
        repository.save(userInfo);
    }
}