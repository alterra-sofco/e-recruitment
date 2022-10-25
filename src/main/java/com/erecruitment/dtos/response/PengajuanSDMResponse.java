package com.erecruitment.dtos.response;

import com.erecruitment.entities.PengajuanSDMSkillEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
public class PengajuanSDMResponse {

    private Long idPengajuan;

    private String posisi;

    private String description;

    private String remarkStaff;

    private String remarkHR;

    private Integer numberRequired;

    private Short status;

    private Integer numberApplicant;

    private Date deadline;

    private Date createdAt;

    private Long userId;

    private String requestName;

    private Set<PengajuanSDMSkillEntity> listSkill;

}
