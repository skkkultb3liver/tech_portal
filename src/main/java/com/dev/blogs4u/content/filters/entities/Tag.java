package com.dev.blogs4u.content.filters.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags_")
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
}
