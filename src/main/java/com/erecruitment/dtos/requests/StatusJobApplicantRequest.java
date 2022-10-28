package com.erecruitment.dtos.requests;

import com.erecruitment.entities.StatusRecruitment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusJobApplicantRequest {

    @ApiModelProperty(notes = "status applicant for filtering hr", required = true)
    private StatusRecruitment status;

    @ApiModelProperty(notes = "description", example = "memahami algoritma dasar", required = false)
    private String description;

}
