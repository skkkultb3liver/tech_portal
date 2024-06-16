package com.dev.blogs4u.content.articles.comments.services.impl;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.articles.comments.dto.CreateCommentRequest;
import com.dev.blogs4u.content.articles.comments.dto.UpdateCommentRequest;
import com.dev.blogs4u.content.articles.comments.entities.Comment;
import com.dev.blogs4u.content.articles.comments.exceptions.CommentException;
import com.dev.blogs4u.content.articles.comments.repositories.CommentRepository;
import com.dev.blogs4u.content.articles.comments.services.ICommentService;
import com.dev.blogs4u.content.articles.entities.Article;
import com.dev.blogs4u.content.articles.services.IArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final IArticleService articleService;

    @Override
    public Comment createComment(CreateCommentRequest request, UserEntity user, Long articleId) {

        Article article = articleService.findArticleById(articleId);

        if (request.getParentCommentId() != null) {
            Comment parentComment = findCommentById(request.getParentCommentId());

            return commentRepository.save(
                Comment.builder()
                    .comment(request.getComment())
                    .parentComment(parentComment)
                    .article(article)
                    .user(user)
                    .build()
            );
        }

        return commentRepository.save(
            Comment.builder()
                .comment(request.getComment())
                .article(article)
                .user(user)
                .build()
        );
    }

    @Override
    public Comment updateComment(UpdateCommentRequest request, UserEntity user) {
        Comment updatedComment = findCommentByIdAndUserId(request.getCommentId(), user.getId());
        updatedComment.setComment(request.getComment());

        return commentRepository.save(updatedComment);
    }

    @Override
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CommentException("Не удалось найти комментарий")
        );
    }

    @Override
    public Comment findCommentByIdAndUserId(Long commentId, Long userId) {
        return commentRepository.findByIdAndUserId(commentId, userId).orElseThrow(
                () -> new CommentException("Не удалось найти комментарий")
        );
    }

    @Override
    public List<Comment> findCommentsByArticleId(Long articleId) {
        return commentRepository.findCommentsByArticleId(articleId).orElseThrow(
                () -> new CommentException("Не удалось найти комментарии")
        );
    }

    @Override
    public void deleteComment(Long commentId, UserEntity user) {
        Comment deletedComment = findCommentByIdAndUserId(commentId, user.getId());
        commentRepository.delete(deletedComment);
    }

}
