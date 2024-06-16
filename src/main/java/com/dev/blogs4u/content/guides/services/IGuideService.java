package com.dev.blogs4u.content.guides.services;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.guides.dto.GuideCreationRequest;
import com.dev.blogs4u.content.guides.dto.GuideUpdateRequest;
import com.dev.blogs4u.content.guides.dto.GuidesFilterRequest;
import com.dev.blogs4u.content.guides.entities.Guide;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGuideService {

    Guide findGuideById(Long guideId);
    Page<Guide> filterGuides(GuidesFilterRequest request);
    Guide createGuide(GuideCreationRequest guideCreationDto, UserEntity user);
    Guide updateGuide(GuideUpdateRequest request, Long guideId, UserEntity user);
    List<Guide> findGuidesByUserUid(String uid);
    List<Guide> getGuidesByQuery(String query);
}
