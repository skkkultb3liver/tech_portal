package com.dev.blogs4u.content.guides.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuideUpdateRequest {

    private String title;
    private String description;
    private int price;
    private List<Long> pLangsIds;

    private List<ModuleCreationRequest> moduleCreationRequests;

}
