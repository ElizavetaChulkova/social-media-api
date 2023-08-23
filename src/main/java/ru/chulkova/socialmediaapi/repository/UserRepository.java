package ru.chulkova.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.socialmediaapi.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = """ 
            SELECT u.id, u.email, u.name, u.password, u.role
            FROM users u JOIN requests r on u.id = r.request_id WHERE r.user_id = :userId
                """, nativeQuery = true)
    List<User> findAllRequestsByUserId(@Param("userId") Long userId);

    @Transactional
    @Override
    User save(User user);

    @Modifying
    @Transactional
    void delete(User user);
}
