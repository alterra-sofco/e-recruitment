package com.erecruitment.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PengajuanSDMResponse {

    private Long idPengajuan;

    private String posisi;

    private String description;

    private String remarkStaff;

    private Integer numberRequired;

    private Short status;

    private Integer numberApplicant;

    private Date deadline;

    private Date createdAt;

}
