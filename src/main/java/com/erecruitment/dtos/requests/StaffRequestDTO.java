package com.erecruitment.dtos.requests;

import com.erecruitment.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequestDTO {

    private Long staffId;

    @NotBlank(message = "user id is needed")
    private User user;

    @NotBlank(message = "department id is needed")
    private Long departmentId;
}
