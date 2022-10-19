package com.erecruitment.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableResponse<T> {

    private String message;

    private String status;

    private T data;

    private Integer currentPage;

    private Integer totalPages;

    private Integer pageSize;

    private Boolean next;

    private Boolean previous;

    private Long totalData;


}
