package com.erecruitment.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPostingDetailResponse extends JobPostingResponseList{
    @JsonProperty("JobDetail")
    private String description;

    private Boolean IsApplied = false;
}
