package com.dev.blogs4u.content.articles.controllers;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.authentication.services.ICustomUserDetailsService;
import com.dev.blogs4u.content.articles.dto.ArticlesFilterByParamsRequest;
import com.dev.blogs4u.content.articles.dto.ArticleDto;
import com.dev.blogs4u.content.articles.entities.Article;
import com.dev.blogs4u.content.articles.services.IArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
@Slf4j
public class ArticleController {

    private final IArticleService articleService;
    private final ICustomUserDetailsService userDetailsService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Article> createArticleHandler(
            @RequestHeader("Authorization") String jwt, @RequestBody ArticleDto createArticleRequest
    ) {

        log.info("{}", createArticleRequest);

        UserEntity user = userDetailsService.findUserByJwt(jwt);
        Article createdArticle = articleService.createArticle(user, createArticleRequest);

        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Article>> filterArticlesHandler(
            @RequestParam(required = false) List<String> tagsSlugs,
            @RequestParam(required = false) List<String> pLangsSlugs,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ) {

        ArticlesFilterByParamsRequest request = ArticlesFilterByParamsRequest.builder()
                .tagsSlugs(tagsSlugs)
                .pLangsSlugs(pLangsSlugs)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Page<Article> filteredArticles = articleService.filterArticles(request);

        return new ResponseEntity<>(filteredArticles, HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Article>> searchArticlesHandler(@RequestParam String query) {

        List<Article> foundedArticles = articleService.searchArticles(query);
        return new ResponseEntity<>(foundedArticles, HttpStatus.FOUND);
    }


    @PutMapping("/{articleId}/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Article> updateArticleHandler(
            @PathVariable Long articleId, @RequestHeader("Authorization") String authHeader, @RequestBody ArticleDto request
    ) {

        UserEntity user = userDetailsService.findUserByJwt(authHeader);
        Article updatedArticle = articleService.updateArticle(user, articleId, request);

        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Article> findArticleHandler(@PathVariable Long articleId) {
        Article foundedArticle = articleService.findArticleById(articleId);

        return new ResponseEntity<>(foundedArticle, HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Article>> findAllArticlesHandler() {
        List<Article> articles = articleService.findAllArticles();

        return new ResponseEntity<>(articles, HttpStatus.FOUND);
    }

}
