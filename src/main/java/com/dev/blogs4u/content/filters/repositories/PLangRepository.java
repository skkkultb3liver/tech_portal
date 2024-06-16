package com.dev.blogs4u.content.filters.repositories;

import com.dev.blogs4u.content.filters.entities.PLang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PLangRepository extends JpaRepository<PLang, Long> {

    Optional<PLang> findBySlug(String slug);

}
