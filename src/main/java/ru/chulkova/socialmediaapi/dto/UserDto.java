package ru.chulkova.socialmediaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.chulkova.socialmediaapi.model.AbstractBaseEntity;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractBaseEntity {

    private String name;
    private String email;
}