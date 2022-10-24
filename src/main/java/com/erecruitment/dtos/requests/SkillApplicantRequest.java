package com.erecruitment.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SkillApplicantRequest {

    @NotBlank(message = "skillName is required")
    private String skillName;
}
