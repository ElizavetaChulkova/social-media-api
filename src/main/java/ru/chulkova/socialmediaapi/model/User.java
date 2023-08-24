package ru.chulkova.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AbstractBaseEntity implements UserDetails {

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    @Size(min = 5, max = 255)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    @NotNull
    @Size(min = 5, max = 255,
            message = "password length must be between 5 and 255")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"),
            uniqueConstraints =
            @UniqueConstraint(columnNames = {"user_id", "friend_id"}, name = "friends_unique_idx")
    )
    private List<User> friends;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "subscribers",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id", referencedColumnName = "id"),
            uniqueConstraints =
            @UniqueConstraint(columnNames = {"user_id", "subscriber_id"}, name = "subscribers_unique_idx")
    )
    private List<User> subscribers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Post> posts;

    @Enumerated(EnumType.STRING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "requests",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "requester_id", referencedColumnName = "id"),
            uniqueConstraints =
            @UniqueConstraint(columnNames = {"user_id", "requester_id"}, name = "requester_unique_idx")
    )
    private List<User> requests;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}