package com.dev.blogs4u.content.filters.services.impl;

import com.dev.blogs4u.content.filters.dto.CreateTagsRequest;
import com.dev.blogs4u.content.filters.entities.Tag;
import com.dev.blogs4u.content.filters.exceptions.TagException;
import com.dev.blogs4u.content.filters.repositories.TagRepository;
import com.dev.blogs4u.content.filters.services.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findTagByName(String name) {
        return tagRepository.findByName(name).orElseThrow(
                () -> new TagException("Не удалось найти тег")
        );
    }

    @Override
    public Tag findTagById(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(
                () -> new TagException("Не удалось найти такой тег")
        );
    }

    @Override
    public void createTags(CreateTagsRequest request) {

        List<Tag> tags = request.getTagsNames().stream().map(
                tagName -> Tag.builder().name(tagName).slug(tagName.toLowerCase().replace(' ', '_')).build()
        ).toList();

        tagRepository.saveAll(tags);
    }
}
