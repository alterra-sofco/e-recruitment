package com.erecruitment.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class StaffRequestDTO {

    private Long staffId;

    @NotBlank(message = "user id is needed")
    private Long userId;

    @NotBlank(message = "department id is needed")
    private Long departmentId;
}
