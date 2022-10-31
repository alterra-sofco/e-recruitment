package com.erecruitment.dtos.requests;

import com.erecruitment.entities.Degree;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class EducationRequest {

    @NotBlank(message = "educationName is required")
    @ApiModelProperty(notes = "name of institute", example = "Universitas Karawang", required = true)
    private String educationName;

    @NotBlank(message = "degree is required")
    @ApiModelProperty(notes = "level of deegree ('HIGH_SCHOOL', 'BACHELOR', 'MASTER', 'DOCTOR', 'PHD')", required = true)
    private Degree degree;

    @NotBlank(message = "major is required")
    @ApiModelProperty(notes = "major in institute", example = "Teknik Informatika", required = true)
    private String major;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotBlank(message = "date is required")
    @ApiModelProperty(notes = "start join in institute", example = "2016-06-01", required = true)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "graduate date in institute, if null currently study here", example = "2020-06-01", required = false)
    private Date endDate;

    @ApiModelProperty(notes = "description", example = "memahami algoritma dasar", required = false)
    private String description;
}
