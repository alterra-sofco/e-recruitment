package com.erecruitment.services.interfaces;

import com.erecruitment.dtos.requests.ApplicantEditProfileRequest;
import com.erecruitment.dtos.requests.EducationRequest;
import com.erecruitment.dtos.requests.ExperienceRequest;
import com.erecruitment.dtos.response.ApplicantProfileResponse;
import com.erecruitment.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IApplicantService {
    ApplicantProfileResponse getUserDetail(User user);

    ApplicantProfileResponse updateUserDetail(User user, ApplicantEditProfileRequest bodyRequest);

    ApplicantProfileResponse uploadAvatar(User user, MultipartFile file) throws IOException;

    ApplicantProfileResponse uploadCv(User user, MultipartFile file) throws IOException;

    ApplicantProfileResponse addEducation(User user, EducationRequest bodyRequest);

    ApplicantProfileResponse updateEducation(Long educationId, EducationRequest bodyRequest, User user);

    ApplicantProfileResponse deleteEducation(Long educationId, User user);

    ApplicantProfileResponse addExperience(User user, ExperienceRequest bodyRequest);

    ApplicantProfileResponse updateExperience(Long experienceId, ExperienceRequest bodyRequest, User user);

    ApplicantProfileResponse deleteExperience(Long experienceId, User user);
}
