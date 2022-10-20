package com.erecruitment.dtos.requests.auth;

import com.erecruitment.entities.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestDTO {
    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    @JsonIgnore
    private RoleName role = RoleName.APPLICANT;
}
