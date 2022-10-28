package com.erecruitment.repositories;

import com.erecruitment.entities.PengajuanSDMEntity;
import com.erecruitment.entities.StatusRecruitment;
import com.erecruitment.entities.Submission;
import com.erecruitment.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Optional<Submission> findByJobPostingAndAndAppliedBy(PengajuanSDMEntity jobPosting, User appliedBy);

    Page<Submission> findByAppliedBy(User appliedBy, Pageable pageable);

    Page<Submission> findByAppliedByAndStatus(User appliedBy, StatusRecruitment status, Pageable pageable);

    Page<Submission> findByJobPosting(PengajuanSDMEntity jobPosting, Pageable pageable);

    Page<Submission> findByJobPostingAndStatus(PengajuanSDMEntity jobPosting, StatusRecruitment status, Pageable pageable);

    Set<Submission> findByStatus(StatusRecruitment status);
}
