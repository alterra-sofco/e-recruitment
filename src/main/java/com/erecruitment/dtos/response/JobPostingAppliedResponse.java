package com.erecruitment.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPostingAppliedResponse extends JobPostingResponseList {
    private Short status;
}
