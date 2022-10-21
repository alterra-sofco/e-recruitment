package com.erecruitment.dtos.requests.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserLoginRequestDTO {
    @NotBlank(message = "email is required")
    @Email(message = "email invalid")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
