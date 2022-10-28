package com.erecruitment.dtos.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SkillApplicantRequest {

    @NotBlank(message = "skillName is required")
    @ApiModelProperty(notes = "skill set", example = "Java", required = true)
    private String skillName;
}
