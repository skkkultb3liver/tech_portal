package com.dev.blogs4u.content.guides.repositories;

import com.dev.blogs4u.content.guides.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("select b from Block b where (b.id = :block_id and b.module.id = :module_id)")
    Optional<Block> findByIdAndModuleId(@Param("block_id") Long blockId, @Param("module_id") Long moduleId);

}
