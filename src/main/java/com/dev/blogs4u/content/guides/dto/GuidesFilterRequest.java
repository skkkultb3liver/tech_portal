package com.dev.blogs4u.content.guides.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GuidesFilterRequest {
    private List<String> pLangsSlugs;
    private Long maxPrice;
    private Long minPrice;
    private Integer pageNum;
    private Integer pageSize;
}
