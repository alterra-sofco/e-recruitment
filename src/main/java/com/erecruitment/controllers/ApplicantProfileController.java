package com.erecruitment.controllers;

import com.erecruitment.dtos.requests.ApplicantEditProfileRequest;
import com.erecruitment.dtos.requests.EducationRequest;
import com.erecruitment.dtos.requests.ExperienceRequest;
import com.erecruitment.dtos.response.ApplicantProfileResponse;
import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.ResponseGenerator;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PatchMapping("/basic")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> editProfile(@RequestBody ApplicantEditProfileRequest bodyRequest){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ApplicantProfileResponse response = applicantService.updateUserDetail(user, bodyRequest);

        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "Ok",
                response), HttpStatus.OK);

    }

    @PatchMapping("/avatar")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> uploadProfilePicture(@RequestPart(value = "file", required = true) MultipartFile file){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!file.getContentType().contains("image")) {
            throw new ValidationErrorException("file must be image");
        }

        try {
            ApplicantProfileResponse response = applicantService.uploadAvatar(user, file);
            ResponseGenerator responseGenerator = new ResponseGenerator();

            return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                    "Ok",
                    response), HttpStatus.OK);
        } catch (Exception e) {
            throw new ValidationErrorException("Could not upload this file!");
        }

    }

    @PatchMapping("/cv")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> uploadCv(@RequestPart(value = "file", required = true) MultipartFile file){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!file.getContentType().contains("pdf")) {
            throw new ValidationErrorException("file must be pdf");
        }
        try {
            ApplicantProfileResponse response = applicantService.uploadCv(user, file);
            ResponseGenerator responseGenerator = new ResponseGenerator();

            return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                    "Ok",
                    response), HttpStatus.OK);
        } catch (Exception e) {
            throw new ValidationErrorException("Could not upload this file!");
        }
    }

    @PostMapping("/education")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> createEducational(@RequestBody EducationRequest bodyRequest){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ApplicantProfileResponse response = applicantService.addEducation(user, bodyRequest);
        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.CREATED.value()),
                "success, education created!",
                response), HttpStatus.CREATED);
    }

    @PutMapping("/education/{educationId}")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> updateEducational(@RequestBody EducationRequest bodyRequest,
                                                                                      @PathVariable("educationId") Long educationId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ApplicantProfileResponse response = applicantService.updateEducation(educationId, bodyRequest, user);
        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "success, education data updated!",
                response), HttpStatus.OK);

    }

    @DeleteMapping("/education/{educationId}")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> deleteEducational(@PathVariable("educationId") Long educationId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ApplicantProfileResponse response = applicantService.deleteEducation(educationId, user);

        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "success, education data deleted!",
                response), HttpStatus.OK);
    }

    @PostMapping("experience")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> createExperience(@RequestBody ExperienceRequest bodyRequest){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ApplicantProfileResponse response = applicantService.addExperience(user, bodyRequest);
        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.CREATED.value()),
                "success, experience created!",
                response), HttpStatus.CREATED);
    }

    @PutMapping("experience/{experienceId}")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> updateExperience(@PathVariable("experienceId") Long experienceId,
                                                                                     @RequestBody ExperienceRequest bodyRequest){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ApplicantProfileResponse response = applicantService.updateExperience(experienceId, bodyRequest, user);
        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "success, experience data updated!",
                response), HttpStatus.OK);

    }

    @DeleteMapping("experience/{experienceId}")
    public ResponseEntity<CommonResponse<ApplicantProfileResponse>> deleteExperience(@PathVariable("experienceId") Long experienceId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ApplicantProfileResponse response = applicantService.deleteExperience(experienceId, user);

        ResponseGenerator responseGenerator = new ResponseGenerator();

        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()),
                "success, experience data deleted!",
                response), HttpStatus.OK);
    }

}
