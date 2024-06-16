package com.dev.blogs4u.content.filters.services;

import com.dev.blogs4u.content.filters.dto.CreateTagsRequest;
import com.dev.blogs4u.content.filters.entities.Tag;

import java.util.List;

public interface ITagService {

    Tag saveTag(Tag tag);
    List<Tag> findAllTags();
    Tag findTagByName(String name);
    Tag findTagById(Long tagId);
    void createTags(CreateTagsRequest request);
}
