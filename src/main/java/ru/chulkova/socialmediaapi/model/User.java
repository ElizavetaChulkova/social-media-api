package ru.chulkova.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints =
@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractBaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id")
    )
    private List<User> friends;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "subscribers",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
    )
    private List<User> subscribers;
}
