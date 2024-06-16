package com.dev.blogs4u.content.filters.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "plangs_")
@Entity
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class PLang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String slug;

}
