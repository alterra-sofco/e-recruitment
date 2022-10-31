package com.erecruitment.controllers;

import com.erecruitment.apachePoi.JobPostingExcelExporter;
import com.erecruitment.dtos.requests.StatusJobApplicantRequest;
import com.erecruitment.dtos.requests.WebSocketDTO;
import com.erecruitment.dtos.response.*;
import com.erecruitment.entities.RoleName;
import com.erecruitment.entities.StatusRecruitment;
import com.erecruitment.entities.Submission;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.PermissionErrorException;
import com.erecruitment.repositories.SubmissionRepository;
import com.erecruitment.services.interfaces.IApplicantService;
import com.erecruitment.services.interfaces.IJobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (user.getRole() != RoleName.ADMIN) {
            throw new PermissionErrorException("Not have permission");
        }
        jobPostingService.setStatus(submissionId, bodyRequest);

        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "Success, data setted!",
                null), HttpStatus.OK);
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/applyJob")
    public WebSocketDTO addUser(@Payload WebSocketDTO chatMessage, SimpMessageHeaderAccessor accessor) {
        accessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping("/export-applicant/{jobPostingId}")
    public void exportToExcel(HttpServletResponse response, @PathVariable("jobPostingId") Long jobPostingId) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        List<Submission> appliedListResponses = jobPostingService.exportAppliedListToExcel(jobPostingId);
        String header = "Content-Disposition";
        String headerValue = "attachment; filename=" + appliedListResponses.get(1).getJobPosting() + " " + currentDateTime + ".xlsx";
        response.setHeader(header, headerValue);

        JobPostingExcelExporter excelExporter = new JobPostingExcelExporter(appliedListResponses);
        excelExporter.export(response);
    }

}
