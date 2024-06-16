package com.dev.blogs4u.content.guides.dto;

import com.dev.blogs4u.content.guides.entities.Module;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuideCreationRequest {

    private String title;
    private String description;
    private int price;
    private List<Long> pLangsIds;

    private List<ModuleCreationRequest> modulesRequests;
}
