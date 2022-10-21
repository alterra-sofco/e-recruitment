package com.erecruitment.services;

import com.erecruitment.entities.File;
import com.erecruitment.repositories.FileRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FileService.class})
@ExtendWith(SpringExtension.class)
class FileServiceTest {
    @MockBean
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @MockBean
    private ModelMapper modelMapper;

    /**
     * Method under test: {@link FileService#store(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testStore() throws IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: No current ServletRequestAttributes
        //       at org.springframework.util.Assert.state(Assert.java:76)
        //       at org.springframework.web.servlet.support.ServletUriComponentsBuilder.getCurrentRequest(ServletUriComponentsBuilder.java:179)
        //       at org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath(ServletUriComponentsBuilder.java:147)
        //       at com.erecruitment.services.FileService.store(FileService.java:36)
        //   In order to prevent store(MultipartFile)
        //   from throwing IllegalStateException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   store(MultipartFile).
        //   See https://diff.blue/R013 to resolve this issue.

        File file = new File();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        file.setData("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        file.setDeleted(true);
        file.setDisplayName("Display Name");
        file.setFileId(123L);
        file.setType("Type");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(fileRepository.save(any())).thenReturn(file);
        fileService.store(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes(StandardCharsets.UTF_8))));
    }

    /**
     * Method under test: {@link FileService#store(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testStore2() throws IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.erecruitment.services.FileService.store(FileService.java:28)
        //   In order to prevent store(MultipartFile)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   store(MultipartFile).
        //   See https://diff.blue/R013 to resolve this issue.

        File file = new File();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        file.setData("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        file.setDeleted(true);
        file.setDisplayName("Display Name");
        file.setFileId(123L);
        file.setType("Type");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(fileRepository.save(any())).thenReturn(file);
        fileService.store(null);
    }

    /**
     * Method under test: {@link FileService#store(MultipartFile)}
     */
    @Test
    void testStore3() throws IOException {
        File file = new File();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        file.setData("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        file.setDeleted(true);
        file.setDisplayName("Display Name");
        file.setFileId(123L);
        file.setType("Type");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(fileRepository.save(any())).thenReturn(file);
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getBytes()).thenThrow(new IOException("An error occurred"));
        when(multipartFile.getContentType()).thenReturn("text/plain");
        when(multipartFile.getOriginalFilename()).thenReturn("foo.txt");
        assertThrows(IOException.class, () -> fileService.store(multipartFile));
        verify(multipartFile).getBytes();
        verify(multipartFile).getContentType();
        verify(multipartFile).getOriginalFilename();
    }

    /**
     * Method under test: {@link FileService#downloadFile(Long)}
     */
    @Test
    void testDownloadFile() throws UnsupportedEncodingException {
        File file = new File();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        file.setData("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        file.setDeleted(true);
        file.setDisplayName("Display Name");
        file.setFileId(123L);
        file.setType("Type");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<File> ofResult = Optional.of(file);
        when(fileRepository.findById(any())).thenReturn(ofResult);
        assertSame(file, fileService.downloadFile(123L));
        verify(fileRepository).findById(any());
    }

    /**
     * Method under test: {@link FileService#downloadFile(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDownloadFile2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:148)
        //       at com.erecruitment.services.FileService.downloadFile(FileService.java:48)
        //   In order to prevent downloadFile(Long)
        //   from throwing NoSuchElementException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   downloadFile(Long).
        //   See https://diff.blue/R013 to resolve this issue.

        when(fileRepository.findById(any())).thenReturn(Optional.empty());
        fileService.downloadFile(123L);
    }

    /**
     * Method under test: {@link FileService#getFile(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetFile() throws UnsupportedEncodingException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: No current ServletRequestAttributes
        //       at org.springframework.util.Assert.state(Assert.java:76)
        //       at org.springframework.web.servlet.support.ServletUriComponentsBuilder.getCurrentRequest(ServletUriComponentsBuilder.java:179)
        //       at org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath(ServletUriComponentsBuilder.java:147)
        //       at com.erecruitment.services.FileService.getFile(FileService.java:55)
        //   In order to prevent getFile(Long)
        //   from throwing IllegalStateException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   getFile(Long).
        //   See https://diff.blue/R013 to resolve this issue.

        File file = new File();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        file.setData("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        file.setDeleted(true);
        file.setDisplayName("Display Name");
        file.setFileId(123L);
        file.setType("Type");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<File> ofResult = Optional.of(file);
        when(fileRepository.findById(any())).thenReturn(ofResult);
        fileService.getFile(123L);
    }
}

