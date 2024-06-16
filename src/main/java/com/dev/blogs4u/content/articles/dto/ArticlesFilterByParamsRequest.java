package com.dev.blogs4u.content.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ArticlesFilterByParamsRequest {
    private List<String> tagsSlugs;
    private List<String> pLangsSlugs;
    private Integer pageNum;
    private Integer pageSize;
}
