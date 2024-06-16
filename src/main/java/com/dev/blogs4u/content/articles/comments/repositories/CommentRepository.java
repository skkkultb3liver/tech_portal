package com.dev.blogs4u.content.articles.comments.repositories;

import com.dev.blogs4u.content.articles.comments.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.id = :commentId and c.user.id = :userId")
    Optional<Comment> findByIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);

    @Query("select c from Comment c where c.article.id = :articleId order by c.dateOfCreated desc ")
    Optional<List<Comment>> findCommentsByArticleId(@Param("articleId") Long articleId);

}
