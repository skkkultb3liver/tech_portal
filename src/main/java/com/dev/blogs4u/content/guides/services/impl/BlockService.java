package com.dev.blogs4u.content.guides.services.impl;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.guides.dto.BlockCreationRequest;
import com.dev.blogs4u.content.guides.dto.BlockUpdateRequest;
import com.dev.blogs4u.content.guides.entities.Block;
import com.dev.blogs4u.content.guides.repositories.BlockRepository;
import com.dev.blogs4u.content.guides.services.IBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService implements IBlockService {

    private final BlockRepository blockRepository;

    @Override
    public Block createBlock(BlockCreationRequest request, UserEntity user) {

        Block createdBlock = Block.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        return blockRepository.save(createdBlock);

    }

    @Override
    public Block updateBlock(BlockUpdateRequest request, UserEntity user) {
        return null;
    }

    @Override
    public Block getBlockById(Long blockId) {
        return null;
    }

    @Override
    public List<Block> findByModuleId(Long moduleId) {
        return null;
    }
}
