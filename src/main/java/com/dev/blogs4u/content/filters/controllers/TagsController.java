package com.dev.blogs4u.content.filters.controllers;

import com.dev.blogs4u.content.filters.dto.CreateTagsRequest;
import com.dev.blogs4u.content.filters.entities.Tag;
import com.dev.blogs4u.content.filters.services.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagsController {

    private final ITagService tagService;

    @GetMapping("/")
    public ResponseEntity<List<Tag>> findAllTagsHandler() {
        return new ResponseEntity<>(tagService.findAllTags(), HttpStatus.FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTagsHandler(@RequestBody CreateTagsRequest request) {
        tagService.createTags(request);

        return new ResponseEntity<>("Теги были успещно созданы", HttpStatus.CREATED);
    }

}
