package com.dev.blogs4u.content.articles.mappers;

import com.dev.blogs4u.content.articles.dto.ArticleDto;
import com.dev.blogs4u.content.articles.entities.Article;

public interface IArticleMapper {

    Article articleDtoToArticle(ArticleDto articleDto);

}
