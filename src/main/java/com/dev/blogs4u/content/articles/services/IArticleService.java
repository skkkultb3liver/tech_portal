package com.dev.blogs4u.content.articles.services;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.articles.dto.ArticlesFilterByParamsRequest;
import com.dev.blogs4u.content.articles.dto.ArticleDto;
import com.dev.blogs4u.content.filters.entities.Tag;
import com.dev.blogs4u.content.articles.entities.Article;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IArticleService {

    Article createArticle(UserEntity user, ArticleDto createArticleRequest);

    Article updateArticle(UserEntity user, Long articleId, ArticleDto request);

    Article findArticleById(Long articleId);

    Article findArticleByIdAndUserId(Long articleId, Long userId);

    List<Article> findAllArticles();

    List<Article> findByTagsIn(List<Tag> tags);

    Page<Article> filterArticles(ArticlesFilterByParamsRequest request);

    List<Article> searchArticles(String query);
}
