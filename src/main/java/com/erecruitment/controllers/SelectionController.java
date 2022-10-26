package com.erecruitment.controllers;

import com.erecruitment.dtos.requests.StatusJobApplicantRequest;
import com.erecruitment.dtos.response.*;
import com.erecruitment.entities.StatusRecruitment;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.repositories.SubmissionRepository;
import com.erecruitment.services.interfaces.IApplicantService;
import com.erecruitment.services.interfaces.IJobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/hr-selection")
public class SelectionController {

    @Autowired
    private IJobPostingService jobPostingService;

    @Autowired
    private IApplicantService applicantService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @GetMapping("/{jobPostingId}")
    public ResponseEntity<PageableResponse<JobAppliedListResponse>> getApplicantList(
            @RequestParam(required = false) StatusRecruitment status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable("jobPostingId") Long jobPostingId) {

        PageableResponse responseList = jobPostingService.getApplicantJobPosting(page, size, status, jobPostingId);

        responseList.setStatus(String.valueOf(HttpStatus.OK.value()));
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/detail/{submissionId}")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> getProfileApplicant(@PathVariable("submissionId") Long submissionId) {
        User user = submissionRepository.findById(submissionId).orElseThrow(() -> new DataNotFoundException("submission not found")).getAppliedBy();
        ApplicantProfileResponse response = applicantService.getUserDetail(user);

        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "Ok",
                response), HttpStatus.OK);
    }

    @PutMapping("/detail/{submissionId}")
    public ResponseEntity<CommonResponse> setJobStatusApplicant(@PathVariable("submissionId") Long submissionId,
                                                                @RequestBody StatusJobApplicantRequest bodyRequest
    ) {
        jobPostingService.setStatus(submissionId, bodyRequest);

        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "Success, data setted!",
                null), HttpStatus.OK);
    }

}
