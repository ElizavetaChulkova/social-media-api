package ru.chulkova.socialmediaapi.mapper;

import ru.chulkova.socialmediaapi.dto.PostDto;
import ru.chulkova.socialmediaapi.model.AbstractBaseEntity;
import ru.chulkova.socialmediaapi.model.Post;

public class PostMapper extends AbstractBaseEntity {

    public static PostDto getTo(Post post) {
        return PostDto.builder()
                .title(post.getTitle())
                .text(post.getText())
                .image(post.getImage())
                .author(UserMapper.getTo(post.getUser()))
                .dateTime(post.getDateTime())
                .build();
    }
}