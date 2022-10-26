package com.erecruitment.services;

import com.erecruitment.dtos.response.FileResponse;
import com.erecruitment.entities.File;
import com.erecruitment.exceptions.DataNotFoundException;
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
    public FileResponse store(MultipartFile file) throws IOException {
        File fileData = upload(file);
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
    public File upload(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File FileDB = new File();
        FileDB.setDisplayName(fileName);
        FileDB.setType(file.getContentType());
        FileDB.setData(file.getBytes());
        File fileData = fileRepository.save(FileDB);
        return fileData;
    }

    @Override
    public File uploadChange(MultipartFile file, Long fileId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileData = fileRepository.findById(fileId).orElseThrow(() -> new DataNotFoundException("file not found"));
        fileData.setDisplayName(fileName);
        fileData.setType(file.getContentType());
        fileData.setData(file.getBytes());
        return fileRepository.save(fileData);
    }

    @Override
    public File downloadFile(Long fileId) {
        return fileRepository.findById(fileId).get();
    }

    @Override
    public FileResponse getFile(Long fileId) {
        Optional<File> file = fileRepository.findById(fileId);
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

    @Override
    public String generateUrlFile(Long fileId) {
        Optional<File> file = fileRepository.findById(fileId);
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/file/download/")
                .path(file.get().getFileId().toString())
                .toUriString();
    }


    private FileResponse fileConvertToDto(File file) {
        return modelMapper.map(file, FileResponse.class);
    }
}
