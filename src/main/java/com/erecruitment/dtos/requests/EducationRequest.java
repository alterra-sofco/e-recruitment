package com.erecruitment.dtos.requests;

import com.erecruitment.entities.Degree;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
public class EducationRequest {

    @NotBlank( message =  "educationName is required")
    private String educationName;

    @NotBlank( message = "degree is required")
    private Degree degree;

    @NotBlank( message = "major is required")
    private String major;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    private String description;
}
