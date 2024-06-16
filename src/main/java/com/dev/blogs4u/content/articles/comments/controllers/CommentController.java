package com.dev.blogs4u.content.articles.comments.controllers;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.authentication.services.ICustomUserDetailsService;
import com.dev.blogs4u.content.articles.comments.dto.CreateCommentRequest;
import com.dev.blogs4u.content.articles.comments.dto.UpdateCommentRequest;
import com.dev.blogs4u.content.articles.comments.entities.Comment;
import com.dev.blogs4u.content.articles.comments.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles/comments")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;
    private final ICustomUserDetailsService userDetailsService;

    @GetMapping("/{articleId}")
    public ResponseEntity<List<Comment>> getArticleCommentsHandler(@PathVariable Long articleId) {
        List<Comment> articleComments = commentService.findCommentsByArticleId(articleId);

        return new ResponseEntity<>(articleComments, HttpStatus.FOUND);
    }

    @PostMapping("{articleId}/create")
    public ResponseEntity<Comment> createCommentHandler(
            @RequestBody CreateCommentRequest request,
            @PathVariable Long articleId,
            @RequestHeader("Authorization") String authHeader
    ) {

        UserEntity user = userDetailsService.findUserByJwt(authHeader);
        Comment createdComment = commentService.createComment(request, user, articleId);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Comment> updateCommentHandler(@RequestBody UpdateCommentRequest request, @RequestHeader("Authorization") String authHeader) {

        UserEntity user = userDetailsService.findUserByJwt(authHeader);
        Comment updatedComment = commentService.updateComment(request, user);

        return new ResponseEntity<>(updatedComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteCommentHandler(@PathVariable Long commentId, @RequestHeader("Authorization") String authHeader) {

        UserEntity user = userDetailsService.findUserByJwt(authHeader);
        commentService.deleteComment(commentId, user);

        return new ResponseEntity<>("Комментарий был успешно удален", HttpStatus.I_AM_A_TEAPOT);
    }

}
