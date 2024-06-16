package com.dev.blogs4u.content.articles.comments.services;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.articles.comments.dto.CreateCommentRequest;
import com.dev.blogs4u.content.articles.comments.entities.Comment;
import com.dev.blogs4u.content.articles.comments.dto.UpdateCommentRequest;

import java.util.List;

public interface ICommentService {

    Comment createComment(CreateCommentRequest request, UserEntity user, Long articleId);

    Comment updateComment(UpdateCommentRequest request, UserEntity user);

    Comment findCommentById(Long commentId);

    Comment findCommentByIdAndUserId(Long commentId, Long userId);

    List<Comment> findCommentsByArticleId(Long articleId);

    void deleteComment(Long commentId, UserEntity user);
}
