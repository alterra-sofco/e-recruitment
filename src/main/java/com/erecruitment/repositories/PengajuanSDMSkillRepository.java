package com.erecruitment.repositories;

import com.erecruitment.entities.PengajuanSDMSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PengajuanSDMSkillRepository extends JpaRepository<PengajuanSDMSkillEntity, Long> {

    @Query(value = "SELECT p.* FROM pengajuan_sdm_skill_required p where p.pengajuan_id = :pengajuan", nativeQuery = true)
    Set<PengajuanSDMSkillEntity> findByPengajuanId(@Param("pengajuan") Long id);
}
