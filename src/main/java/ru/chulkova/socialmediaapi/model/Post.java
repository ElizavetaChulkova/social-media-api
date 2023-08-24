package ru.chulkova.socialmediaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AbstractBaseEntity {

    @Column(name = "title")
    @NotNull
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime dateTime;

    @Lob
    @Column(name = "text")
    @NotNull
    private String text;

    @Lob
    @Column(name = "image")
    @NotNull
    private String image;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}