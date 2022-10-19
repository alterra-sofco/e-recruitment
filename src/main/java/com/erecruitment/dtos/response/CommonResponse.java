package com.erecruitment.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> {
    private String status;
    private String message;
    private T data;
}
