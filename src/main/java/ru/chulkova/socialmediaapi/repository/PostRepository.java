package ru.chulkova.socialmediaapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.socialmediaapi.model.Post;

import java.util.List;

@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p from Post p WHERE p.user.id = :userId")
    List<Post> findAllByUserId(@Param("userId") Long userId);

    @Transactional
    @Override
    Post save(Post post);

    @Transactional
    @Modifying
    @Query("DELETE FROM Post p WHERE p.id = :postId")
    void deleteById(@Param("postId") Long postId);

    @Query(value = """
            SELECT * FROM post p
            WHERE p.user_id IN (
                SELECT f.friend_id FROM friends f JOIN users u on u.id = f.friend_id
                                                           WHERE f.user_id = :userId
                )
            """, nativeQuery = true)
    List<Post> getPostsByFriends(@Param("userId") Long userId, Pageable pageable);
}
