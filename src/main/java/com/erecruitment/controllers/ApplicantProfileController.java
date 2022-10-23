package com.erecruitment.controllers;

import com.erecruitment.dtos.requests.ApplicantEditProfileRequest;
import com.erecruitment.dtos.response.ApplicantProfileResponse;
import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.ResponseGenerator;
import com.erecruitment.entities.User;
import com.erecruitment.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/applicant")
public class ApplicantProfileController {

    @Autowired
    private ApplicantService applicantService;

    @GetMapping
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> getProfile(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ApplicantProfileResponse response = applicantService.getUserDetail(user);

        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "Ok",
                response), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> editProfile(@RequestBody ApplicantEditProfileRequest bodyRequest){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ApplicantProfileResponse response = applicantService.updateUserDetail(user, bodyRequest);

        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "Ok",
                response), HttpStatus.OK);

    }
}
