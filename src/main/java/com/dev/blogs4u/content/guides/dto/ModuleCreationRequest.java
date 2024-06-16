package com.dev.blogs4u.content.guides.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleCreationRequest {

    private String title;
    private String description;
    private Long guideId;
}
