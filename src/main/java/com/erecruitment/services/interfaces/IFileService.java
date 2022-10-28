package com.erecruitment.services.interfaces;

import com.erecruitment.dtos.response.FileResponse;
import com.erecruitment.entities.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    FileResponse store(MultipartFile file) throws IOException;

    File upload(MultipartFile file) throws IOException;

    File uploadChange(MultipartFile file, Long fileId) throws IOException;

    File downloadFile(Long fileId);

    FileResponse getFile(Long fileId);

    String generateUrlFile(Long fileId);
}
