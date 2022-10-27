package com.erecruitment.controllers;

import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.DashboardSummaryResponse;
import com.erecruitment.dtos.response.ResponseGenerator;
import com.erecruitment.services.interfaces.IJobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardSummaryController {

    @Autowired
    private IJobPostingService jobPostingService;

    @GetMapping
    public ResponseEntity<CommonResponse<DashboardSummaryResponse>> getSummaryDashboard() {

        DashboardSummaryResponse response = jobPostingService.getSummary();

        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "Success, data setted!",
                response), HttpStatus.OK);
    }
}
