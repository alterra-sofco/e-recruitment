package com.erecruitment.repositories;

import com.erecruitment.entities.PengajuanSDMEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PengajuanSDMRepository extends JpaRepository<PengajuanSDMEntity, Long> {

    //related to staff entity,
    List<PengajuanSDMEntity> findByStaff (Long staffId);

}
