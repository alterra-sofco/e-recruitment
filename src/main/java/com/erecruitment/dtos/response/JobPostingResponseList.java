package com.erecruitment.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class JobPostingResponseList implements Serializable {

    @JsonProperty("jobPostingId")
    private Long idPengajuan;

    @JsonProperty("jobPosition")
    private String posisi;

    private Integer yearExperience = 1;

    private Date updatedAt;
}
