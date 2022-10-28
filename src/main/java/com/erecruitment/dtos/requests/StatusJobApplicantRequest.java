package com.erecruitment.dtos.requests;

import com.erecruitment.entities.StatusRecruitment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusJobApplicantRequest {

    private StatusRecruitment status;

    private String description;

}
