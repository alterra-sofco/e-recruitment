package com.erecruitment.dtos.response;

import com.erecruitment.entities.PengajuanSDMEntity;
import com.erecruitment.entities.StatusRecruitment;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JobAppliedHistoryResponse{

    private StatusRecruitment status;

    private Date appliedAt;

    private Date updatedAt;

    private JobPostingAppliedResponse jobPosting;
}
