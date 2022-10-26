package com.erecruitment.services.interfaces;

import com.erecruitment.dtos.requests.JobApplyRequest;
import com.erecruitment.dtos.response.JobPostingDetailResponse;
import com.erecruitment.dtos.response.PageableResponse;
import com.erecruitment.entities.StatusRecruitment;

public interface IJobPostingService {
    PageableResponse getAllJobPosting(int page, int size, String keyword);

    JobPostingDetailResponse getJobById(Long jobPostingId);

    Object applyJob(Long jobPostingId, JobApplyRequest bodyRequest);

    PageableResponse getHistoryJobPosting(int page, int size, StatusRecruitment status);
}
