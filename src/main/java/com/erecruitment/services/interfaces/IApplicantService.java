package com.erecruitment.services.interfaces;

import com.erecruitment.dtos.requests.ApplicantEditProfileRequest;
import com.erecruitment.dtos.response.ApplicantProfileResponse;
import com.erecruitment.entities.User;

public interface IApplicantService {
    ApplicantProfileResponse getUserDetail(User user);

    ApplicantProfileResponse updateUserDetail(User user, ApplicantEditProfileRequest bodyRequest);
}
