package com.erecruitment.dtos.response;

import com.erecruitment.entities.StatusRecruitment;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JobAppliedListResponse {
    private Long submissionId;

    private StatusRecruitment status;

    private Date appliedAt;

    private Date updatedAt;

    private String coverLetter;

    private String description;

    private UserList appliedBy;

}

@Setter
@Getter
class UserList {
    private String name;

    private String email;

    private String phoneNumber;
}
