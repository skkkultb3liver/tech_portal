package com.dev.blogs4u.content.articles.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateCommentRequest {
    private String comment;
    private Long parentCommentId;
}
