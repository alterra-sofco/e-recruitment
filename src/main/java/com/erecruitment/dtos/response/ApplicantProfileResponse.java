package com.erecruitment.dtos.response;

import com.erecruitment.entities.Education;
import com.erecruitment.entities.Experience;
import com.erecruitment.entities.SkillEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ApplicantProfileResponse {

    private Long userId;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String bio;

    private Date dob;

    private String avatarURL;

    private String avatarFileId;

    private String cvURL;

    private String cvFileId;

    private Set<Education> educations;

    private Set<Experience> experiences;

    private Set<SkillEntity> skills;

}
