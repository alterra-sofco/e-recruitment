package com.erecruitment.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class ExperienceRequest {

    @NotBlank(message = "corporateName is required")
    @ApiModelProperty(notes = "name of corporate", example = "Sofco Graha", required = true)
    private String corporateName;

    @NotBlank(message = "position is required")
    @ApiModelProperty(notes = "position or title job", example = "Backend engineer", required = true)
    private String position;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotBlank(message = "date is required")
    @ApiModelProperty(notes = "start joining corporate", example = "2021-06-01", required = true)
    private Date startDate;

    @ApiModelProperty(notes = "end date joining corporate,  if null currently working this corporate", example = "2022-06-01", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(notes = "description", example = "memahami algoritma dasar", required = false)
    private String description;
}
