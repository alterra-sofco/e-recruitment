package com.erecruitment.services;

import com.erecruitment.dtos.requests.ApplicantEditProfileRequest;
import com.erecruitment.dtos.requests.EducationRequest;
import com.erecruitment.dtos.requests.ExperienceRequest;
import com.erecruitment.dtos.requests.SkillApplicantRequest;
import com.erecruitment.dtos.response.ApplicantProfileResponse;
import com.erecruitment.entities.*;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.ApplicantRepository;
import com.erecruitment.repositories.EducationRepository;
import com.erecruitment.repositories.ExperienceRepository;
import com.erecruitment.repositories.UserRepository;
import com.erecruitment.repositories.SkillRepository;
import com.erecruitment.services.interfaces.IApplicantService;
import com.erecruitment.services.interfaces.IFileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicantService implements IApplicantService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IFileService fileService;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public ApplicantProfileResponse getUserDetail(User user) {
        Applicant applicant = applicantRepository.findByOwnedBy(user).orElseGet(() -> {
            Applicant profile = new Applicant();
            profile.setOwnedBy(user);
            return profile;
        });

        ApplicantProfileResponse response = conversationalistProfileResponse(applicant);

       return response;
    }

    @Override
    @Transactional
    public ApplicantProfileResponse updateUserDetail(User user, ApplicantEditProfileRequest bodyRequest) {
        Applicant applicant = applicantRepository.findByOwnedBy(user).orElseGet(() -> {
            Applicant profile = new Applicant();
            profile.setOwnedBy(user);
            return profile;
        });
        user.setName(bodyRequest.getName());
        user.setPhoneNumber(bodyRequest.getPhoneNumber());

        applicant.setDob(bodyRequest.getDob());
        applicant.setAddress(bodyRequest.getAddress());
        applicant.setBio(bodyRequest.getBio());

        userRepository.save(user);

        ApplicantProfileResponse response = getUserDetail(user);
        return response;

    }

    @Override
    public ApplicantProfileResponse uploadAvatar(User user, MultipartFile file) throws IOException {
        if (!file.getContentType().contains("image")) {
            throw new ValidationErrorException("file must be image");
        }

        Applicant applicant = applicantRepository.findByOwnedBy(user).orElseGet(() -> {
            Applicant profile = new Applicant();
            profile.setOwnedBy(user);
            return profile;
        });

        File fileData = new File();

        if (applicant.getAvatar() == null){
            fileData = fileService.upload(file);
        }
        else {
            fileData = fileService.uploadChange(file, applicant.getAvatar().getFileId());
        }


        applicant.setAvatar(fileData);
        applicantRepository.save(applicant);

        return getUserDetail(user);
    }

    @Override
    public ApplicantProfileResponse uploadCv(User user, MultipartFile file) throws IOException {
        if (!file.getContentType().contains("pdf")) {
            throw new ValidationErrorException("file must be pdf");
        }

        Applicant applicant = applicantRepository.findByOwnedBy(user).orElseGet(() -> {
            Applicant profile = new Applicant();
            profile.setOwnedBy(user);
            return profile;
        });

        File fileData = new File();

        if (applicant.getCv() == null){
            fileData = fileService.upload(file);
        }
        else {
            fileData = fileService.uploadChange(file, applicant.getCv().getFileId());
        }

        applicant.setCv(fileData);
        applicantRepository.save(applicant);

        return getUserDetail(user);
    }

    @Override
    public ApplicantProfileResponse addEducation(User user, EducationRequest bodyRequest) {
        Education education = convertToEducationEntity(bodyRequest);
        education.setOwnedBy(user);
        educationRepository.save(education);
        return getUserDetail(user);
    }

    @Override
    public ApplicantProfileResponse updateEducation(Long educationId, EducationRequest bodyRequest, User user) {
        Education education = educationRepository.findByIdaAndOwnedBy(educationId, user).orElseThrow(() -> new DataNotFoundException("educationId not found!"));

        //update data education
        education.setEducationName(bodyRequest.getEducationName());
        education.setDegree(bodyRequest.getDegree());
        education.setMajor(bodyRequest.getMajor());
        education.setStartDate(bodyRequest.getStartDate());
        education.setEndDate(bodyRequest.getEndDate());
        education.setDescription(bodyRequest.getDescription());

        educationRepository.save(education);
        return getUserDetail(user);
    }

    @Override
    public ApplicantProfileResponse deleteEducation(Long educationId, User user) {
        Education education = educationRepository.findByIdaAndOwnedBy(educationId, user).orElseThrow(() -> new DataNotFoundException("educationId not found!"));
        educationRepository.deleteById(education.getEducationId());
        return getUserDetail(user);
    }

    @Override
    public ApplicantProfileResponse addExperience(User user, ExperienceRequest bodyRequest) {
        Experience experience = convertToExperienceEntity(bodyRequest);
        experience.setOwnedBy(user);
        experienceRepository.save(experience);
        return getUserDetail(user);
    }

    @Override
    public ApplicantProfileResponse updateExperience(Long experienceId, ExperienceRequest bodyRequest, User user) {
        Experience experience = experienceRepository.findByIdaAndOwnedBy(experienceId, user).orElseThrow(() -> new DataNotFoundException("experienceId not found!"));

        //update data experience
        experience.setCorporateName(bodyRequest.getCorporateName());
        experience.setPosition(bodyRequest.getPosition());
        experience.setDescription(bodyRequest.getDescription());
        experience.setStartDate(bodyRequest.getStartDate());
        experience.setEndDate(bodyRequest.getEndDate());

        experienceRepository.save(experience);
        return getUserDetail(user);
    }

    @Override
    public ApplicantProfileResponse deleteExperience(Long experienceId, User user) {
        Experience experience = experienceRepository.findByIdaAndOwnedBy(experienceId, user).orElseThrow(() -> new DataNotFoundException("experienceId not found!"));
        experienceRepository.deleteById(experience.getExperienceId());
        return getUserDetail(user);
    }

    @Override
    public ApplicantProfileResponse addSkill(User user, SkillApplicantRequest bodyRequest) {
        Applicant applicant = applicantRepository.findByOwnedBy(user).orElseGet(() -> {
            Applicant profile = new Applicant();
            profile.setOwnedBy(user);
            return profile;
        });

        SkillEntity skill = skillRepository.findBySkillName(bodyRequest.getSkillName()).orElseGet(() -> {
            SkillEntity skillEntity = new SkillEntity();
            skillEntity.setSkillName(bodyRequest.getSkillName());
            skillRepository.save(skillEntity);
            return skillEntity;
        });

        applicant.getSkills().add(skill);
        applicantRepository.save(applicant);

        return getUserDetail(user);
    }

    private ApplicantProfileResponse conversationalistProfileResponse(Applicant applicant){
        ApplicantProfileResponse response = modelMapper.map(applicant, ApplicantProfileResponse.class);

        //generate basic user data
        response.setUserId(applicant.getOwnedBy().getUserId());
        response.setName(applicant.getOwnedBy().getName());
        response.setPhoneNumber(applicant.getOwnedBy().getPhoneNumber());
        response.setEmail(applicant.getOwnedBy().getEmail());

        //get url avatar file
        if (applicant.getAvatar() != null){
            response.setAvatarURL(fileService.generateUrlFile(applicant.getAvatar().getFileId()));
        }

        //get url cv file
        if (applicant.getCv() != null){
            response.setCvURL(fileService.generateUrlFile(applicant.getCv().getFileId()));
        }

        //generate educations data
        Set<Education> educations = educationRepository.findByOwnedBy(applicant.getOwnedBy());
        response.setEducations(educations);

        //generate educations data
        Set<Experience> experiences = experienceRepository.findByOwnedBy(applicant.getOwnedBy());
        response.setExperiences(experiences);

        return response;
    }

    private Education convertToEducationEntity(EducationRequest bodyRequest){
        return modelMapper.map(bodyRequest, Education.class);
    }

    private Experience convertToExperienceEntity(ExperienceRequest bodyRequest){
        return modelMapper.map(bodyRequest, Experience.class);
    }


}
