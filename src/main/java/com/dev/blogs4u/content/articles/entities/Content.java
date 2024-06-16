package com.dev.blogs4u.content.articles.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Content {

    private String name;
    private String size;
    private String path;

}