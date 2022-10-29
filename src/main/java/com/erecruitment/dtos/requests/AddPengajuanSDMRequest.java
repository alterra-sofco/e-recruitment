package com.erecruitment.dtos.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddPengajuanSDMRequest {

    @ApiModelProperty(notes = "position for open recruitment", example = "Admin", required = true)
    private String posisi;

    @ApiModelProperty(notes = "Job detail description", example = "membutuhkan untuk posisi admin", required = false)
    private String description;

    @ApiModelProperty(notes = "notes from staff user", example = "dibutuhkan segera", required = false)
    private String remarkStaff;

    @ApiModelProperty(notes = "total human resource needed ", example = "10", required = true)
    private Integer numberRequired;

    @ApiModelProperty(notes = "list skill id get from master skill", example = "[1,2]", required = true)
    private List<Long> listSkill;
}
