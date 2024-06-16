package com.dev.blogs4u.content.guides.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockCreationRequest {

    private String title;
    private String content;
    private Long moduleId;
}
