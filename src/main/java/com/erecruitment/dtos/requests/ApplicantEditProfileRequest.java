package com.erecruitment.dtos.requests;

import com.erecruitment.entities.Education;
import com.erecruitment.entities.Experience;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ApplicantEditProfileRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    private String address;

    private String bio;

    @Past(message = "date not valid")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;
}
