package ru.chulkova.socialmediaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.chulkova.socialmediaapi.model.AbstractBaseEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PostDto extends AbstractBaseEntity {

    private String title;
    private String text;
    private String image;
    private UserDto author;
    private LocalDateTime dateTime;
}