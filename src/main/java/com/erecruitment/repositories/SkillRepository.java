package com.erecruitment.repositories;

import com.erecruitment.entities.SkillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, Long> {

    Page<SkillEntity> findAll(Pageable pageable);

    Page<SkillEntity> findBySkillNameContainingIgnoreCase(String skillName, Pageable pageable);

    List<SkillEntity> findBySkillIdIn(List<Long> id);
}
