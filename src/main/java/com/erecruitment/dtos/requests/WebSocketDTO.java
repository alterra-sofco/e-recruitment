package com.erecruitment.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketDTO {
    private String type;
    private String title;
    private String sender;
    private Long userId;
}
