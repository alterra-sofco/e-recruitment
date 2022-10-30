package com.erecruitment.services.interfaces;

import com.erecruitment.dtos.requests.JobApplyRequest;
import com.erecruitment.dtos.requests.StatusJobApplicantRequest;
import com.erecruitment.dtos.response.DashboardSummaryResponse;
import com.erecruitment.dtos.response.JobAppliedListResponse;
import com.erecruitment.dtos.response.JobPostingDetailResponse;
import com.erecruitment.dtos.response.PageableResponse;
import com.erecruitment.entities.StatusRecruitment;
import com.erecruitment.entities.Submission;

import java.util.List;

public interface IJobPostingService {
    PageableResponse getAllJobPosting(int page, int size, String keyword);

    JobPostingDetailResponse getJobById(Long jobPostingId);

    Object applyJob(Long jobPostingId, JobApplyRequest bodyRequest);

    PageableResponse getHistoryJobPosting(int page, int size, StatusRecruitment status);

    PageableResponse getApplicantJobPosting(int page, int size, StatusRecruitment status, Long jobPostingId);

    Object setStatus(Long submissionId, StatusJobApplicantRequest bodyRequest);

    DashboardSummaryResponse getSummary();

    List<Submission> exportAppliedListToExcel(Long jobPostingId);
}
