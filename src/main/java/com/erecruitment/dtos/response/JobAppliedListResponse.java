package com.erecruitment.dtos.response;

import com.erecruitment.entities.Applicant;
import com.erecruitment.entities.StatusRecruitment;
import com.erecruitment.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Set;

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
