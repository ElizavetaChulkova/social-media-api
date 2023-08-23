package ru.chulkova.socialmediaapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image")
public class Image extends AbstractBaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Lob
    @Column(name = "image")
    byte[] content;
}
