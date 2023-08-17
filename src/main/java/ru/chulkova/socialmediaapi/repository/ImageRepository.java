package ru.chulkova.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chulkova.socialmediaapi.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
