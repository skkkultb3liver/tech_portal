package com.dev.blogs4u.content.articles.repositories;

import com.dev.blogs4u.content.filters.entities.PLang;
import com.dev.blogs4u.content.filters.entities.Tag;
import com.dev.blogs4u.content.articles.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByTitle(String title);

    @Query("select a from Article a join a.tags t where t in :tags")
    Optional<List<Article>> findByTagsIn(@Param("tags") List<Tag> tags);

    @Query("select a from Article a where a.id = :articleId and a.user.id = :userId")
    Optional<Article> findByIdAndUserId(@Param("articleId") Long articleId, @Param("userId") Long userId);

    @Query(
            "select a from Article a " +
            "inner join a.tags t on (:tagsSlugs is null or t.slug in :tagsSlugs)" +
            "inner join a.pLangs p on (:pLangsSlugs is null or p.slug in :pLangsSlugs)"
    )
    Optional<List<Article>> filterArticles(
            @Param("tagsSlugs") List<String> tagsSlugs,
            @Param("pLangsSlugs") List<String> pLangsSlugs
    );

    @Query(
            "select a from Article a where a.title like %:query%"
    )
    Optional<List<Article>> findArticlesByQuery(@Param("query") String query);
}
