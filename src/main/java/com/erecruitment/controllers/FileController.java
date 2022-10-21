package com.erecruitment.controllers;

import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.FileResponse;
import com.erecruitment.dtos.response.ResponseGenerator;
import com.erecruitment.entities.File;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.services.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse<FileResponse>> uploadFile(@RequestPart(value = "file", required = true) MultipartFile file) {
        ResponseGenerator responseGenerator = new ResponseGenerator();
        try {
            return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.CREATED.value()), "Success, file uploaded!", fileService.store(file)), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ValidationErrorException("Could not upload this file!");
        }
    }

    @GetMapping("{fileId}")
    public ResponseEntity<CommonResponse<FileResponse>> getFile(@PathVariable Long fileId) {
        ResponseGenerator responseGenerator = new ResponseGenerator();
        FileResponse file = fileService.getFile(fileId);
        return ResponseEntity.ok(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()), "OK", file));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId, @RequestParam(defaultValue = "1") boolean Isview) {
        File file = fileService.downloadFile(fileId);

        if(file.getType().contains("image") && Isview){
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(file.getData());
        }
        else if (file.getType().contains("pdf")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(file.getData());
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getDisplayName() + "\"")
                .body(file.getData());
    }

}
