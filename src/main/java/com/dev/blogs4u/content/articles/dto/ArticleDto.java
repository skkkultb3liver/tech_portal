package com.dev.blogs4u.content.articles.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleDto {

    @NonNull
    private String title;


    private String description;
    private List<Long> tagsIds;

    private List<Long> pLangsIds;

}
