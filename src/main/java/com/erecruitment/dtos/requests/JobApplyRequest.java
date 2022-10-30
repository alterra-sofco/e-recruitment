package com.erecruitment.dtos.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplyRequest {

    @ApiModelProperty(notes = "Cover Letter", example = "memahami algoritma dasar", required = false)
    private String coverLetter;
}
