package com.erecruitment.services;

import com.erecruitment.dtos.requests.JobApplyRequest;
import com.erecruitment.dtos.requests.StatusJobApplicantRequest;
import com.erecruitment.dtos.requests.WebSocketDTO;
import com.erecruitment.dtos.response.*;
import com.erecruitment.entities.PengajuanSDMEntity;
import com.erecruitment.entities.StatusRecruitment;
import com.erecruitment.entities.Submission;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.CredentialErrorException;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.PengajuanSDMRepository;
import com.erecruitment.repositories.SubmissionRepository;
import com.erecruitment.services.interfaces.IJobPostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobPostingService implements IJobPostingService {
    @Autowired
    private PengajuanSDMRepository pengajuanSDMRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SubmissionRepository submissionRepository;


    @Override
    public PageableResponse getAllJobPosting(int page, int size, String keyword) {
        Page<PengajuanSDMEntity> jobPosting;
        Sort sort = Sort.by("updatedAt").descending();
        Pageable paging = PageRequest.of(page, size, sort);
        jobPosting = keyword != null ? pengajuanSDMRepository.findByPosisiContainingIgnoreCaseAndStatus(keyword, (short) 3, paging) :
                pengajuanSDMRepository.findByStatus((short) 3, paging);

        List<PengajuanSDMEntity> dataList = jobPosting.getContent();
        PageableResponse response = new PageableResponse();
        if (!dataList.isEmpty()) {
            response.setMessage("ok");
            List<JobPostingResponseList> dt = dataList.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            response.setData(dt);
        } else {
            List<JobPostingResponseList> dt = Collections.emptyList();
            response.setData(dt);
        }
        response.setTotalData(jobPosting.getTotalElements());
        response.setTotalPages(jobPosting.getTotalPages());
        response.setCurrentPage(jobPosting.getNumber() + 1);
        response.setNext(jobPosting.hasNext());
        response.setPrevious(jobPosting.hasPrevious());
        response.setPageSize(jobPosting.getSize());

        return response;
    }

    @Override
    public JobPostingDetailResponse getJobById(Long jobPostingId) {
        PengajuanSDMEntity jobDetail = pengajuanSDMRepository.findByIdPengajuanAndStatus(jobPostingId, (short) 3).orElseThrow(() ->
                new DataNotFoundException("data job not found!"));
        JobPostingDetailResponse response = modelMapper.map(jobDetail, JobPostingDetailResponse.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        try {
            user = (User) authentication.getPrincipal();
            Boolean isApplied = submissionRepository.findByJobPostingAndAndAppliedBy(jobDetail, user).isPresent();
            if (isApplied) {
                response.setIsApplied(true);
            }
        } catch (Exception e){
            response.setIsApplied(true);
        }

        return response;
    }

    @Override
    public Object applyJob(Long jobPostingId, JobApplyRequest bodyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            PengajuanSDMEntity jobDetail = pengajuanSDMRepository.findByIdPengajuanAndStatus(jobPostingId, (short) 3).orElseThrow(() ->
                    new DataNotFoundException("data job not found!"));
            Boolean isApplied = submissionRepository.findByJobPostingAndAndAppliedBy(jobDetail, user).isPresent();
            if (isApplied) {
                throw new ValidationErrorException("You are already applied!");
            }

            Submission submission = modelMapper.map(bodyRequest, Submission.class);
            submission.setStatus(StatusRecruitment.APPLIED);
            submission.setAppliedBy(user);
            submission.setJobPosting(jobDetail);
            submissionRepository.save(submission);

            Integer numberApplicant = jobDetail.getNumberApplicant() > 0 ? jobDetail.getNumberApplicant() : 0;
            jobDetail.setNumberApplicant(numberApplicant + 1);
            pengajuanSDMRepository.save(jobDetail);

            User user1 = jobDetail.getUser();
            WebSocketDTO webSocketDTO = new WebSocketDTO();
            webSocketDTO.setSender(user.getName());
            webSocketDTO.setType("newApplyJob");
            webSocketDTO.setTitle(user.getName() + " apply pada posisi " + jobDetail.getPosisi());
            webSocketDTO.setUserId(user1.getUserId());

            return webSocketDTO;
        } else {
            throw new CredentialErrorException("Login required!");
        }
    }

    @Override
    public PageableResponse getHistoryJobPosting(int page, int size, StatusRecruitment status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Page<Submission> jobPosting;
        Sort sort = Sort.by("appliedAt").descending();
        Pageable paging = PageRequest.of(page, size, sort);
        jobPosting = status != null ? submissionRepository.findByAppliedByAndStatus(user, status, paging) :
                submissionRepository.findByAppliedBy(user, paging);

        List<Submission> dataList = jobPosting.getContent();
        PageableResponse response = new PageableResponse();
        if (!dataList.isEmpty()) {
            response.setMessage("ok");
            List<JobAppliedHistoryResponse> dt = dataList.stream()
                    .map(this::convertToHistory)
                    .collect(Collectors.toList());
            response.setData(dt);
        } else {
            List<JobAppliedHistoryResponse> dt = Collections.emptyList();
            response.setData(dt);
        }
        response.setTotalData(jobPosting.getTotalElements());
        response.setTotalPages(jobPosting.getTotalPages());
        response.setCurrentPage(jobPosting.getNumber() + 1);
        response.setNext(jobPosting.hasNext());
        response.setPrevious(jobPosting.hasPrevious());
        response.setPageSize(jobPosting.getSize());

        return response;
    }

    @Override
    public PageableResponse getApplicantJobPosting(int page, int size, StatusRecruitment status, Long jobPostingId) {
        Page<Submission> jobPosting;
        Sort sort = Sort.by("appliedAt").ascending();

        PengajuanSDMEntity jobDetail = pengajuanSDMRepository.findById(jobPostingId).orElseThrow(() ->
                new DataNotFoundException("data job not found!"));

        Pageable paging = PageRequest.of(page, size, sort);
        jobPosting = status != null ? submissionRepository.findByJobPostingAndStatus(jobDetail, status, paging) :
                submissionRepository.findByJobPosting(jobDetail, paging);
        List<Submission> dataList = jobPosting.getContent();
        PageableResponse response = new PageableResponse();
        if (!dataList.isEmpty()) {
            response.setMessage("ok");
            List<JobAppliedListResponse> dt = dataList.stream()
                    .map(this::convertToApplicantList)
                    .collect(Collectors.toList());
            response.setData(dt);
        } else {
            List<JobAppliedListResponse> dt = Collections.emptyList();
            response.setData(dt);
        }
        response.setTotalData(jobPosting.getTotalElements());
        response.setTotalPages(jobPosting.getTotalPages());
        response.setCurrentPage(jobPosting.getNumber() + 1);
        response.setNext(jobPosting.hasNext());
        response.setPrevious(jobPosting.hasPrevious());
        response.setPageSize(jobPosting.getSize());

        return response;
    }

    @Override
    public Object setStatus(Long submissionId, StatusJobApplicantRequest bodyRequest) {
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(() ->
                new DataNotFoundException("submission not found"));
        if (submission.getStatus() == bodyRequest.getStatus()) {
            throw new ValidationErrorException(String.format("submission already %s", submission.getStatus()));
        }
        submission.setStatus(bodyRequest.getStatus());
        submission.setDescription(bodyRequest.getDescription());
        return submissionRepository.save(submission);
    }

    @Override
    public DashboardSummaryResponse getSummary() {

        Set<PengajuanSDMEntity> jobPosting = pengajuanSDMRepository.findByStatus((short) 3);
        List<PengajuanSDMEntity> jobRequest = pengajuanSDMRepository.findAll();
        Set<PengajuanSDMEntity> newJobRequest = pengajuanSDMRepository.findByStatus((short) 1);
        Set<Submission> appliedJob = submissionRepository.findByStatus(StatusRecruitment.APPLIED);

        DashboardSummaryResponse response = new DashboardSummaryResponse(jobPosting.size(), jobRequest.size(), newJobRequest.size(), appliedJob.size());
        return response;
    }

    @Override
    //export xlsx
    public List<Submission> exportAppliedListToExcel(Long jobPostingId){
        Page<Submission> jobPosting;
        Sort sort = Sort.by("appliedAt").ascending();

        PengajuanSDMEntity jobDetail = pengajuanSDMRepository.findById(jobPostingId).orElseThrow(() ->
                new DataNotFoundException("data job not found!"));

        Pageable paging = PageRequest.of(0, 1000, sort);
        jobPosting = submissionRepository.findByJobPosting(jobDetail, paging);
        List<Submission> dataList = jobPosting.getContent();

        return dataList;
    }

    private JobPostingResponseList convertToDto(PengajuanSDMEntity pengajuanSDMEntity) {
        JobPostingResponseList response = modelMapper.map(pengajuanSDMEntity, JobPostingResponseList.class);
        return response;
    }


    private JobAppliedHistoryResponse convertToHistory(Submission dataApplied) {
        return modelMapper.map(dataApplied, JobAppliedHistoryResponse.class);
    }

    private JobAppliedListResponse convertToApplicantList(Submission dataApplied) {
        return modelMapper.map(dataApplied, JobAppliedListResponse.class);
    }


}
