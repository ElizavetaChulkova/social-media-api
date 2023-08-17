package ru.chulkova.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chulkova.socialmediaapi.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
