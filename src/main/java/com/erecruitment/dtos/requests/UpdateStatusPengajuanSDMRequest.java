package com.erecruitment.dtos.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UpdateStatusPengajuanSDMRequest {

    @ApiModelProperty(notes = "status job request 1 => new apply\n" +
            "2 => reject by HR\n" +
            "3 => approve hr/Posted\n" +
            "4 => closed", example = "3", required = true)
    private Short status;

    @ApiModelProperty(notes = "note from HR", example = "memahami algoritma dasar", required = false)
    private String remarkHR;

    @ApiModelProperty(notes = "end date close job posting", example = "2022-11-01", required = false)
    private Date deadline;

}
