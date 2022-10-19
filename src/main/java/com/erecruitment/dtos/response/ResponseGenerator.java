package com.erecruitment.dtos.response;

import org.springframework.stereotype.Component;

@Component
public class ResponseGenerator<T> {

    public <T> CommonResponse<T> responseData(String status, String message, T data) {
        CommonResponse commonResponse = new CommonResponse<>();
        commonResponse.setStatus(status);
        commonResponse.setMessage(message);
        commonResponse.setData(data);
        return commonResponse;
    }
}
