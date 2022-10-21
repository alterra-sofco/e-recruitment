package com.erecruitment.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UpdateStatusPengajuanSDMRequest {

    private Short status;

    private String remarkHR;

    private Date deadline;

}
