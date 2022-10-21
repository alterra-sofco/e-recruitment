package com.erecruitment.services.interfaces;

import com.erecruitment.dtos.response.FileResponse;
import com.erecruitment.entities.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    Object store(MultipartFile file) throws IOException;

    File downloadFile(Long fileId);

    FileResponse getFile(Long fileId);
}
