package com.erecruitment.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddPengajuanSDMRequest {

    private Long idPengajuan;

    private String posisi;

    private String description;

    private String remarkStaff;

    private Integer numberRequired;

    private List<Long> listSkill;
}
