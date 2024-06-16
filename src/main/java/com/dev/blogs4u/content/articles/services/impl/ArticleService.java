package com.dev.blogs4u.content.articles.services.impl;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.articles.dto.ArticlesFilterByParamsRequest;
import com.dev.blogs4u.content.articles.dto.ArticleDto;
import com.dev.blogs4u.content.articles.mappers.IArticleMapper;
import com.dev.blogs4u.content.filters.entities.Tag;
import com.dev.blogs4u.content.articles.exceptions.ArticleException;
import com.dev.blogs4u.content.filters.services.IPLangService;
import com.dev.blogs4u.content.filters.services.ITagService;
import com.dev.blogs4u.content.articles.entities.Article;
import com.dev.blogs4u.content.articles.repositories.ArticleRepository;
import com.dev.blogs4u.content.articles.services.IArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;
    private final ITagService tagService;
    private final IPLangService pLangService;
    private final IArticleMapper articleMapper;

    @Override
    public Article createArticle(UserEntity user, ArticleDto createArticleRequest) {

        Article createdArticle = articleMapper.articleDtoToArticle(createArticleRequest);
        createdArticle.setUser(user);

        return articleRepository.save(createdArticle);
    }

    @Override
    public Article updateArticle(UserEntity user, Long articleId, ArticleDto request) {

        Article updatedArticle = findArticleByIdAndUserId(articleId, user.getId());

        updatedArticle.setTitle(request.getTitle());
        updatedArticle.setDescription(request.getDescription());

        updatedArticle.setTags(request.getTagsIds().stream().map(
                tagService::findTagById
        ).collect(Collectors.toList()));

        return articleRepository.save(updatedArticle);
    }

    @Override
    public Page<Article> filterArticles(ArticlesFilterByParamsRequest request) {

        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize());

        List<Article> articles = articleRepository.filterArticles(request.getTagsSlugs(), request.getPLangsSlugs())
                .orElseThrow(
                        () -> new ArticleException("Не удалось найти соответствующие статьи")
                );

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), articles.size());

        List<Article> pageContent = articles.subList(startIndex, endIndex);
        Page<Article> filteredArticles = new PageImpl<>(pageContent, pageable, articles.size());

        return filteredArticles;
    }

    @Override
    public List<Article> searchArticles(String query) {
        return articleRepository.findArticlesByQuery(query).orElseThrow(
                () -> new ArticleException("Не удалось найти соответствующие статьи")
        );
    }

    @Override
    public Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(
                () -> new UsernameNotFoundException("Не удалось найти статью")
        );
    }

    @Override
    public Article findArticleByIdAndUserId(Long articleId, Long userId) {
        return articleRepository.findByIdAndUserId(articleId, userId).orElseThrow(
                () -> new ArticleException("Не удалось найти статью")
        );
    }

    @Override
    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findByTagsIn(List<Tag> tags) {
        return articleRepository.findByTagsIn(tags).orElseThrow(
                () -> new ArticleException("Не удалось найти соответствующие статьи")
        );
    }




}
