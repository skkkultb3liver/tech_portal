package com.dev.blogs4u.content.guides.repositories;

import com.dev.blogs4u.content.articles.entities.Article;
import com.dev.blogs4u.content.guides.entities.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {

    @Query(
            "select g from Guide g where (g.title like %:query% or g.description like %:query%)"
    )
    Optional<List<Guide>> findGuidesByQuery(@Param("query") String query);

    @Query(
            "select g from Guide g " +
            "inner join g.pLangs p on (:pLangsSlugs is null or p.slug in :pLangsSlugs)" +
            "where ((:maxPrice is null and :minPrice is null) or (g.price between :maxPrice and :minPrice))"
    )
    Optional<List<Guide>> filterGuides(
            @Param("pLangsSlugs") List<String> pLangsSlugs,
            @Param("maxPrice") Long maxPrice,
            @Param("minPrice") Long minPrice
    );

    @Query(
            "select g from Guide g where (:userId = g.user.id and :guideId = g.id)"
    )
    Optional<Guide> findByIdAndUserId(
            @Param("userId") Long userId,
            @Param("guideId") Long guideId
    );

    @Query(
            "select g from Guide g where (:userUid = g.user.uid and :guideId = g.id)"
    )
    Optional<Guide> findByIdAndUserUid(
            @Param("userUid") Long userUid,
            @Param("guideId") Long guideId
    );

}
