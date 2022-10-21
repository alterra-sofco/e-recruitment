package com.erecruitment.services;

import com.erecruitment.dtos.response.FileResponse;
import com.erecruitment.entities.File;
import com.erecruitment.repositories.FileRepository;
import com.erecruitment.services.interfaces.IFileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileService implements IFileService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Object store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File FileDB = new File();
        FileDB.setDisplayName(fileName);
        FileDB.setType(file.getContentType());
        FileDB.setData(file.getBytes());
        File fileData = fileRepository.save(FileDB);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/file/download/")
                .path(fileData.getFileId().toString())
                .toUriString();
        FileResponse response = fileConvertToDto(fileData);
        response.setUrl(fileDownloadUri);
        response.setSize(fileData.getData().length);
        return response;
    }

    @Override
    public File downloadFile(Long fileId) {
        return fileRepository.findById(fileId).get();
    }

    @Override
    public FileResponse getFile(Long fileId) {
        Optional<File> file  = fileRepository.findById(fileId);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/file/download/")
                .path(file.get().getFileId().toString())
                .toUriString();
        FileResponse response = fileConvertToDto(file.get());
        response.setUrl(fileDownloadUri);
        response.setSize(file.get().getData().length);
        return response;
    }

    private FileResponse fileConvertToDto(File file) {
        return modelMapper.map(file, FileResponse.class);
    }
}
