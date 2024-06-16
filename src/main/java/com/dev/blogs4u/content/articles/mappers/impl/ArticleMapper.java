package com.dev.blogs4u.content.articles.mappers.impl;

import com.dev.blogs4u.content.articles.dto.ArticleDto;
import com.dev.blogs4u.content.articles.entities.Article;
import com.dev.blogs4u.content.articles.mappers.IArticleMapper;
import com.dev.blogs4u.content.filters.services.IPLangService;
import com.dev.blogs4u.content.filters.services.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArticleMapper implements IArticleMapper {

    private final IPLangService pLangService;
    private final ITagService tagService;

    @Override
    public Article articleDtoToArticle(ArticleDto articleDto) {

        Article article = Article.builder()
                .title(articleDto.getTitle())
                .description(articleDto.getDescription())

                .pLangs(articleDto.getPLangsIds().stream().map(
                                pLangService::findPLangById)
                        .collect(Collectors.toList()))

                .tags(articleDto.getTagsIds().stream().map(
                        tagService::findTagById)
                        .collect(Collectors.toList())
                )
                .build();

        return article;
    }

}
