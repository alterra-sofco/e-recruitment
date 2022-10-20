package com.erecruitment.dtos.requests.auth;

import com.erecruitment.entities.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegisterRequestDTO {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email invalid")
    private String email;

    private String password;

    private String phoneNumber;

    @JsonIgnore
    private RoleName role = RoleName.APPLICANT;
}
