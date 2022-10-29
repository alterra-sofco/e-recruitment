package com.erecruitment.dtos.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillRequest {

    @ApiModelProperty(notes = "skill master id", example = "1", required = true)
    private Long skillId;

    @ApiModelProperty(notes = "skill set", example = "Java", required = true)
    private String skillName;
}
