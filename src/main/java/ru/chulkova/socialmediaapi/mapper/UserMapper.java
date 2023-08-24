package ru.chulkova.socialmediaapi.mapper;

import ru.chulkova.socialmediaapi.dto.ProfileDto;
import ru.chulkova.socialmediaapi.dto.UserDto;
import ru.chulkova.socialmediaapi.model.User;

public class UserMapper {

    public static ProfileDto getProfile(User user) {
        return ProfileDto.builder()
                .id(user.id())
                .name(user.getName())
                .email(user.getEmail())
                .posts(user.getPosts())
                .friends(user.getFriends())
                .subscribers(user.getSubscribers())
                .build();
    }

    public static UserDto getTo(User user) {
        return UserDto.builder()
                .id(user.id())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}