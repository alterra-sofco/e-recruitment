package com.erecruitment.dtos.requests.auth;

import com.erecruitment.entities.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegisterRequestDTO {

    @NotBlank(message = "name is required")
    @ApiModelProperty(notes = "name", example = "User applicant", required = true)
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email invalid")
    @ApiModelProperty(notes = "email", example = "user@mail.com", required = true)
    private String email;

    @ApiModelProperty(notes = "password", example = "123456", required = true)
    private String password;

    @ApiModelProperty(notes = "phone number", example = "08788665546", required = true)
    private String phoneNumber;

    @JsonIgnore
    private RoleName role = RoleName.APPLICANT;
}
