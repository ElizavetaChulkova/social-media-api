package ru.chulkova.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AbstractBaseEntity{

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "text")
    private String text;

    @PrimaryKeyJoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private Image imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userId;
}
