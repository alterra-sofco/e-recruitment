package com.erecruitment.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
public class ApplicantEditProfileRequest {

    @NotBlank(message = "name is required")
    @ApiModelProperty(notes = "name", example = "User applicant", required = true)
    private String name;

    @NotBlank(message = "phoneNumber is required")
    @ApiModelProperty(notes = "phone number", example = "08788665546", required = true)
    private String phoneNumber;

    @ApiModelProperty(notes = "address", example = "Karawang, Indonesia", required = false)
    private String address;

    @ApiModelProperty(notes = "bio for 'about me'", example = "my name is yanto, and experience Backend engineer", required = false)
    private String bio;

    @Past(message = "date not valid")
    @ApiModelProperty(notes = "date of birth", example = "1997-06-01", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;
}
