package com.erecruitment.services;

import com.erecruitment.dtos.requests.ApplicantEditProfileRequest;
import com.erecruitment.dtos.response.ApplicantProfileResponse;
import com.erecruitment.entities.Applicant;
import com.erecruitment.entities.File;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.repositories.ApplicatRepository;
import com.erecruitment.repositories.FileRepository;
import com.erecruitment.repositories.UserRepository;
import com.erecruitment.services.interfaces.IApplicantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ApplicantService implements IApplicantService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApplicatRepository applicatRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApplicantProfileResponse getUserDetail(User user) {
        Optional applicant = applicatRepository.findByOwnedBy(user);


        if (applicant.isPresent()){
            return null;
        }
        else {
            return applicantUserProfileResponse(user);
        }
    }

    @Override
    @Transactional
    public ApplicantProfileResponse updateUserDetail(User user, ApplicantEditProfileRequest bodyRequest) {
        Optional applicant = applicatRepository.findByOwnedBy(user);
        user.setName(bodyRequest.getName());
        user.setPhoneNumber(bodyRequest.getPhoneNumber());

        if (applicant.isPresent()){
            return null;
        }
        else {
            Applicant result = converttoApplicantEntity(bodyRequest);
            if (bodyRequest.getAvatarFileId() != null){
                File avatar = fileRepository.findById(bodyRequest.getAvatarFileId())
                        .orElseThrow(() -> new DataNotFoundException(String.format("image avatar not found")));
                result.setAvatar(avatar);
            }
            if (bodyRequest.getCvFileId() != null){
                File cv = fileRepository.findById(bodyRequest.getCvFileId())
                        .orElseThrow(() -> new DataNotFoundException(String.format("file cv not found")));
                result.setCv(cv);
            }
            result.setOwnedBy(user);

        }
        userRepository.save(user);
        return null;
    }

    private ApplicantProfileResponse applicantUserProfileResponse(User user) {
        return modelMapper.map(user, ApplicantProfileResponse.class);
    }

    private Applicant converttoApplicantEntity(ApplicantEditProfileRequest bodyRequest) {
        return modelMapper.map(bodyRequest, Applicant.class);
    }


}
