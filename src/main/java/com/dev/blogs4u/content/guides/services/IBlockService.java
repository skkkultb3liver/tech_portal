package com.dev.blogs4u.content.guides.services;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.guides.dto.BlockCreationRequest;
import com.dev.blogs4u.content.guides.dto.BlockUpdateRequest;
import com.dev.blogs4u.content.guides.entities.Block;

import java.util.List;

public interface IBlockService {

    Block createBlock(BlockCreationRequest request, UserEntity user);

    Block updateBlock(BlockUpdateRequest request, UserEntity user);

    Block getBlockById(Long blockId);

    List<Block> findByModuleId(Long moduleId);
}
