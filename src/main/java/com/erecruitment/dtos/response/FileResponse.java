package com.erecruitment.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {
    private Long fileId;

    private String displayName;

    private String url;

    private String type;

    private long size;
}
