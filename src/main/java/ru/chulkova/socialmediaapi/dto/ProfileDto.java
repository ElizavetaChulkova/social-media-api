package ru.chulkova.socialmediaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.chulkova.socialmediaapi.model.AbstractBaseEntity;
import ru.chulkova.socialmediaapi.model.Post;
import ru.chulkova.socialmediaapi.model.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProfileDto extends AbstractBaseEntity {

    private String name;
    private String email;
    private List<Post> posts;
    private List<User> friends;
    private List<User> subscribers;
}