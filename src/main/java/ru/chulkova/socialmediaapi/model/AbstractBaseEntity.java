package ru.chulkova.socialmediaapi.model;

import jakarta.persistence.*;
import org.springframework.util.Assert;

@MappedSuperclass
public abstract class AbstractBaseEntity {

    @Id
    @SequenceGenerator(
            name = "user_id_seq",
            sequenceName = "user_id_seq"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_seq"
    )
    private Long id;

    public long id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }
}
