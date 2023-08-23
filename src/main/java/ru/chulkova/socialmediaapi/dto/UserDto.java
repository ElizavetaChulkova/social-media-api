package ru.chulkova.socialmediaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chulkova.socialmediaapi.model.AbstractBaseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractBaseEntity {

    private String name;
    private String email;
}