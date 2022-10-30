package com.erecruitment.dtos.requests.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserLoginRequestDTO {
    @NotBlank(message = "email is required")
    @Email(message = "email invalid")
    @ApiModelProperty(notes = "email", example = "admin@mail.com", required = true)
    private String email;

    @NotBlank(message = "password is required")
    @ApiModelProperty(notes = "password", example = "123456", required = true)
    private String password;
}
