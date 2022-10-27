package com.erecruitment.controllers;

import com.erecruitment.dtos.requests.JobApplyRequest;
import com.erecruitment.dtos.requests.WebSocketDTO;
import com.erecruitment.dtos.response.*;
import com.erecruitment.entities.StatusRecruitment;
import com.erecruitment.exceptions.CredentialErrorException;
import com.erecruitment.services.interfaces.IJobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
public class JobPostingController {

    @Autowired
    private IJobPostingService jobPostingService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    @GetMapping
    public ResponseEntity<PageableResponse<JobPostingResponseList>> getAllJobPosting(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageableResponse responseList = jobPostingService.getAllJobPosting(page, size, keyword);

        responseList.setStatus(String.valueOf(HttpStatus.OK.value()));
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{jobPostingId}")
    public ResponseEntity<CommonResponse<JobPostingDetailResponse>> getJobDetail(@PathVariable("jobPostingId") Long jobPostingId) {
        JobPostingDetailResponse response = jobPostingService.getJobById(jobPostingId);

        ResponseGenerator responseGenerator = new ResponseGenerator();


        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "Ok",
                response), HttpStatus.OK);
    }

    @PostMapping("/{jobPostingId}/apply")
    public ResponseEntity<CommonResponse> applyJob(@PathVariable("jobPostingId") Long jobPostingId,
                                                   @RequestBody JobApplyRequest bodyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            WebSocketDTO webSocketDTO = (WebSocketDTO) jobPostingService.applyJob(jobPostingId, bodyRequest);
            messagingTemplate.convertAndSend("/topic/applyJob", webSocketDTO);
        } else {
            throw new CredentialErrorException("Login required!");
        }

        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "success, job applied!",
                null), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<PageableResponse<JobPostingResponseList>> getHistoryJobPosting(
            @RequestParam(required = false) StatusRecruitment status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageableResponse responseList = jobPostingService.getHistoryJobPosting(page, size, status);

        responseList.setStatus(String.valueOf(HttpStatus.OK.value()));
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
