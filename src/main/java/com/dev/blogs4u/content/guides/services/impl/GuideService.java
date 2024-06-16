package com.dev.blogs4u.content.guides.services.impl;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.authentication.services.ICustomUserDetailsService;
import com.dev.blogs4u.content.filters.services.IPLangService;
import com.dev.blogs4u.content.guides.dto.GuideCreationRequest;
import com.dev.blogs4u.content.guides.dto.GuideUpdateRequest;
import com.dev.blogs4u.content.guides.dto.GuidesFilterRequest;
import com.dev.blogs4u.content.guides.entities.Guide;
import com.dev.blogs4u.content.guides.entities.Module;
import com.dev.blogs4u.content.guides.exceptions.GuideException;
import com.dev.blogs4u.content.guides.repositories.GuideRepository;
import com.dev.blogs4u.content.guides.services.IGuideService;
import com.dev.blogs4u.content.guides.services.IModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GuideService implements IGuideService {

    private final GuideRepository guideRepository;
    private final IPLangService pLangService;
    private final IModuleService moduleService;
    private final ICustomUserDetailsService userDetailsService;

    @Override
    public Guide findGuideById(Long guideId) {
        return guideRepository.findById(guideId).orElseThrow(
                () -> new GuideException("Не удалось найти данный гайд")
        );
    }

    @Override
    public Page<Guide> filterGuides(GuidesFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize());

        List<Guide> guideList = guideRepository.filterGuides(request.getPLangsSlugs(),
                        request.getMaxPrice(), request.getMinPrice())
                .orElseThrow(
                        () -> new GuideException("Не удалось найти данные гайды")
                );

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), guideList.size());

        List<Guide> pageContent = guideList.subList(startIndex, endIndex);
        Page<Guide> filteredGuides = new PageImpl<>(pageContent, pageable, guideList.size());

        return filteredGuides;
    }

    @Override
    public Guide createGuide(GuideCreationRequest request, UserEntity user) {

        List<Module> guideModules = new ArrayList<>();

        if (!request.getModulesRequests().isEmpty()) {

            guideModules = request.getModulesRequests().stream().map(
                    moduleService::createModule
            ).toList();

        }

        Guide createdGuide = Guide.builder()
                .price(request.getPrice())
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .modules(guideModules)
                .pLangs(
                        request.getPLangsIds().stream()
                                .map(pLangService::findPLangById)
                                .collect(Collectors.toList()))
                .build();

        return guideRepository.save(createdGuide);
    }

    @Override
    public Guide updateGuide(GuideUpdateRequest request, Long guideId, UserEntity user) {

        Guide updatedGuide = guideRepository.findByIdAndUserId(guideId, user.getId()).orElseThrow(
                () -> new GuideException("Не удалось найти соответствующий гайд")
        );

        updatedGuide.setTitle(request.getTitle());
        updatedGuide.setDescription(request.getDescription());
        updatedGuide.setPrice(request.getPrice());

        updatedGuide.setPLangs(
                request.getPLangsIds().stream()
                        .map(pLangService::findPLangById)
                        .collect(Collectors.toList())
        );

        if (!request.getModuleCreationRequests().isEmpty()) {

            List<Module> newModules = request.getModuleCreationRequests().stream().map(
                    moduleService::createModule
            ).toList();

            for (Module module:newModules) {
                updatedGuide.getModules().add(module);
            }
        }

        return guideRepository.save(updatedGuide);


    }

    @Override
    public List<Guide> findGuidesByUserUid(String uid) {

        UserEntity user = userDetailsService.findUserByUid(uid);
        List<Guide> userGuides = user.getPurchasedGuides();

        return userGuides;
    }


    @Override
    public List<Guide> getGuidesByQuery(String query) {
        return guideRepository.findGuidesByQuery(query).orElseThrow(
                () -> new GuideException("Не удалось найти соответствующие гайды")
        );
    }

}
