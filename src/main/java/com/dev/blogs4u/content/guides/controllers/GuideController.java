package com.dev.blogs4u.content.guides.controllers;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.authentication.services.ICustomUserDetailsService;
import com.dev.blogs4u.content.guides.dto.GuideCreationRequest;
import com.dev.blogs4u.content.guides.dto.GuideUpdateRequest;
import com.dev.blogs4u.content.guides.dto.GuidesFilterRequest;
import com.dev.blogs4u.content.guides.entities.Guide;
import com.dev.blogs4u.content.guides.services.IGuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/guides")
public class GuideController {

    private final IGuideService guideService;
    private final ICustomUserDetailsService userDetailsService;

    @GetMapping("/{guideId}")
    private ResponseEntity<Guide> getGuideByIdHandler(@PathVariable Long guideId) {
        Guide guide = guideService.findGuideById(guideId);

        return new ResponseEntity<>(guide, HttpStatus.FOUND);
    }

    @GetMapping("/")
    private ResponseEntity<Page<Guide>> getGuidesHandler(
            @RequestParam(required = false) List<String> pLangsSlugs,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Long minPrice,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ) {

        GuidesFilterRequest request = GuidesFilterRequest.builder()
                .pLangsSlugs(pLangsSlugs)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Page<Guide> guides = guideService.filterGuides(request);

        return new ResponseEntity<>(guides, HttpStatus.FOUND);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    private ResponseEntity<Guide> createGuideHandler(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody GuideCreationRequest request
    ) {

        UserEntity user = userDetailsService.findUserByJwt(authHeader);
        Guide createdGuide = guideService.createGuide(request, user);

        return new ResponseEntity<>(createdGuide, HttpStatus.CREATED);
    }

    @PutMapping("{guideId}/update")
    @PreAuthorize("hasRole('USER')")
    private ResponseEntity<Guide> updateGuideHandler(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody GuideUpdateRequest request,
            @PathVariable Long guideId
    ) {

        UserEntity user = userDetailsService.findUserByJwt(authHeader);
        Guide updateGuide = guideService.updateGuide(request, guideId, user);

        return new ResponseEntity<>(updateGuide, HttpStatus.CREATED);
    }

    //    @GetMapping("{uid}/guides")
    //    private ResponseEntity<List<Guide>> findGuidesByUserUidHandler(
    //            @PathVariable String uid
    //    ) {
    //
    //        List<Guide> guides = guideService.findGuidesByUserUid(uid);
    //
    //        return new ResponseEntity<>(guides, HttpStatus.FOUND);
    //
    //    }
}
