package com.erecruitment.repositories;

import com.erecruitment.entities.SkillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, Long> {

    Page<SkillEntity> findAll(Pageable pageable);

    Page<SkillEntity> findBySkillNameContainingIgnoreCase(String skillName, Pageable pageable);

    List<SkillEntity> findBySkillIdIn(List<Long> id);

    @Query( "select x from SkillEntity x where lower(x.skillName) = lower(:skillName)")
    Optional<SkillEntity> findBySkillName(String skillName);
}
