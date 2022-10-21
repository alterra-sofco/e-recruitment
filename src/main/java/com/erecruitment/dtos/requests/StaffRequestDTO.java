package com.erecruitment.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StaffRequestDTO {

    private Long staffId;

    @NotBlank(message = "user id is needed")
    private Long userId;

    @NotBlank(message = "department id is needed")
    private Long departmentId;
}
