package com.erecruitment.repositories;

import com.erecruitment.entities.PengajuanSDMEntity;
import com.erecruitment.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PengajuanSDMRepository extends JpaRepository<PengajuanSDMEntity, Long> {

    Page<PengajuanSDMEntity> findAll(Pageable pageable);

    Page<PengajuanSDMEntity> findByUser(User user, Pageable pageable);

    Page<PengajuanSDMEntity> findByUserAndStatus(User user, Short status, Pageable pageable);

    Page<PengajuanSDMEntity> findByUserAndPosisiContainingIgnoreCase(User user, String posisi, Pageable pageable);

    Page<PengajuanSDMEntity> findByUserAndPosisiContainingIgnoreCaseAndStatus(User user, String posisi, Short status, Pageable pageable);

    Page<PengajuanSDMEntity>
    findByPosisiContainingIgnoreCase(String posisi, Pageable pageable);

    Page<PengajuanSDMEntity> findByPosisiContainingIgnoreCaseAndStatus(String posisi, Short status, Pageable pageable);

    Page<PengajuanSDMEntity> findByStatus(Short status, Pageable paging);
}
