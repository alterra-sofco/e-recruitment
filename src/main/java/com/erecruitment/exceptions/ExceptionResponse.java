package com.erecruitment.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExceptionResponse<Object> {
    private String status;
    private String message;
    private List<Object> data = new ArrayList<>();
}
