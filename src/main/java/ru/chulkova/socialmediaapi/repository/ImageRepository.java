package ru.chulkova.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.socialmediaapi.model.Image;

import java.util.Optional;

@Transactional(readOnly = true)
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByName(String name);

    @Transactional
    @Override
    Image save(Image image);

    @Transactional
    @Modifying
    void delete(Image image);
}
