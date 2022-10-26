package com.erecruitment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyShort;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.erecruitment.dtos.requests.JobApplyRequest;
import com.erecruitment.dtos.requests.StatusJobApplicantRequest;
import com.erecruitment.dtos.response.DashboardSummaryResponse;
import com.erecruitment.dtos.response.JobAppliedListResponse;
import com.erecruitment.dtos.response.JobPostingDetailResponse;
import com.erecruitment.dtos.response.JobPostingResponseList;
import com.erecruitment.dtos.response.PageableResponse;
import com.erecruitment.entities.PengajuanSDMEntity;
import com.erecruitment.entities.RoleName;
import com.erecruitment.entities.StatusRecruitment;
import com.erecruitment.entities.Submission;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.PengajuanSDMRepository;
import com.erecruitment.repositories.SubmissionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JobPostingService.class})
@ExtendWith(SpringExtension.class)
class JobPostingServiceTest {
    @Autowired
    private JobPostingService jobPostingService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PengajuanSDMRepository pengajuanSDMRepository;

    @MockBean
    private SubmissionRepository submissionRepository;

    /**
     * Method under test: {@link JobPostingService#getAllJobPosting(int, int, String)}
     */
    @Test
    void testGetAllJobPosting() {
        when(pengajuanSDMRepository.findByPosisiContainingIgnoreCaseAndStatus((String) any(), (Short) any(),
                (Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        PageableResponse actualAllJobPosting = jobPostingService.getAllJobPosting(1, 3, "Keyword");
        assertEquals(1, actualAllJobPosting.getCurrentPage().intValue());
        assertEquals(1, actualAllJobPosting.getTotalPages().intValue());
        assertEquals(0L, actualAllJobPosting.getTotalData().longValue());
        assertFalse(actualAllJobPosting.getPrevious());
        assertEquals(0, actualAllJobPosting.getPageSize().intValue());
        assertFalse(actualAllJobPosting.getNext());
        assertTrue(((Collection<Object>) actualAllJobPosting.getData()).isEmpty());
        verify(pengajuanSDMRepository).findByPosisiContainingIgnoreCaseAndStatus((String) any(), (Short) any(),
                (Pageable) any());
    }

    /**
     * Method under test: {@link JobPostingService#getAllJobPosting(int, int, String)}
     */
    @Test
    void testGetAllJobPosting2() {
        JobPostingResponseList jobPostingResponseList = new JobPostingResponseList();
        jobPostingResponseList.setIdPengajuan(1L);
        jobPostingResponseList.setPosisi("Posisi");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        jobPostingResponseList.setUpdatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        jobPostingResponseList.setYearExperience(1);
        when(modelMapper.map((Object) any(), (Class<JobPostingResponseList>) any())).thenReturn(jobPostingResponseList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("updatedAt");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);

        PengajuanSDMEntity pengajuanSDMEntity = new PengajuanSDMEntity();
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setCreatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setDeadline(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setDescription("The characteristics of someone or something");
        pengajuanSDMEntity.setIdPengajuan(1L);
        pengajuanSDMEntity.setNumberApplicant(10);
        pengajuanSDMEntity.setNumberRequired(10);
        pengajuanSDMEntity.setPosisi("updatedAt");
        pengajuanSDMEntity.setRemarkHR("updatedAt");
        pengajuanSDMEntity.setStatus((short) 1);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setUpdatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setUser(user);

        ArrayList<PengajuanSDMEntity> pengajuanSDMEntityList = new ArrayList<>();
        pengajuanSDMEntityList.add(pengajuanSDMEntity);
        PageImpl<PengajuanSDMEntity> pageImpl = new PageImpl<>(pengajuanSDMEntityList);
        when(pengajuanSDMRepository.findByPosisiContainingIgnoreCaseAndStatus((String) any(), (Short) any(),
                (Pageable) any())).thenReturn(pageImpl);
        PageableResponse actualAllJobPosting = jobPostingService.getAllJobPosting(1, 3, "Keyword");
        assertEquals(1, actualAllJobPosting.getCurrentPage().intValue());
        assertEquals(1, actualAllJobPosting.getTotalPages().intValue());
        assertEquals(1L, actualAllJobPosting.getTotalData().longValue());
        assertFalse(actualAllJobPosting.getPrevious());
        assertEquals(1, actualAllJobPosting.getPageSize().intValue());
        assertFalse(actualAllJobPosting.getNext());
        assertEquals("ok", actualAllJobPosting.getMessage());
        assertEquals(1, ((Collection<JobPostingResponseList>) actualAllJobPosting.getData()).size());
        verify(modelMapper).map((Object) any(), (Class<JobPostingResponseList>) any());
        verify(pengajuanSDMRepository).findByPosisiContainingIgnoreCaseAndStatus((String) any(), (Short) any(),
                (Pageable) any());
    }

    /**
     * Method under test: {@link JobPostingService#getAllJobPosting(int, int, String)}
     */
    @Test
    void testGetAllJobPosting3() {
        JobPostingResponseList jobPostingResponseList = new JobPostingResponseList();
        jobPostingResponseList.setIdPengajuan(1L);
        jobPostingResponseList.setPosisi("Posisi");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        jobPostingResponseList.setUpdatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        jobPostingResponseList.setYearExperience(1);
        when(modelMapper.map((Object) any(), (Class<JobPostingResponseList>) any())).thenReturn(jobPostingResponseList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("updatedAt");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);

        PengajuanSDMEntity pengajuanSDMEntity = new PengajuanSDMEntity();
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setCreatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setDeadline(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setDescription("The characteristics of someone or something");
        pengajuanSDMEntity.setIdPengajuan(1L);
        pengajuanSDMEntity.setNumberApplicant(10);
        pengajuanSDMEntity.setNumberRequired(10);
        pengajuanSDMEntity.setPosisi("updatedAt");
        pengajuanSDMEntity.setRemarkHR("updatedAt");
        pengajuanSDMEntity.setStatus((short) 1);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setUpdatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setUser(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setName("updatedAt");
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRole(RoleName.USER);
        user1.setUserId(123L);

        PengajuanSDMEntity pengajuanSDMEntity1 = new PengajuanSDMEntity();
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity1.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity1.setDeadline(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity1.setDescription("The characteristics of someone or something");
        pengajuanSDMEntity1.setIdPengajuan(1L);
        pengajuanSDMEntity1.setNumberApplicant(10);
        pengajuanSDMEntity1.setNumberRequired(10);
        pengajuanSDMEntity1.setPosisi("updatedAt");
        pengajuanSDMEntity1.setRemarkHR("updatedAt");
        pengajuanSDMEntity1.setStatus((short) 1);
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity1.setUpdatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity1.setUser(user1);

        ArrayList<PengajuanSDMEntity> pengajuanSDMEntityList = new ArrayList<>();
        pengajuanSDMEntityList.add(pengajuanSDMEntity1);
        pengajuanSDMEntityList.add(pengajuanSDMEntity);
        PageImpl<PengajuanSDMEntity> pageImpl = new PageImpl<>(pengajuanSDMEntityList);
        when(pengajuanSDMRepository.findByPosisiContainingIgnoreCaseAndStatus((String) any(), (Short) any(),
                (Pageable) any())).thenReturn(pageImpl);
        PageableResponse actualAllJobPosting = jobPostingService.getAllJobPosting(1, 3, "Keyword");
        assertEquals(1, actualAllJobPosting.getCurrentPage().intValue());
        assertEquals(1, actualAllJobPosting.getTotalPages().intValue());
        assertEquals(2L, actualAllJobPosting.getTotalData().longValue());
        assertFalse(actualAllJobPosting.getPrevious());
        assertEquals(2, actualAllJobPosting.getPageSize().intValue());
        assertFalse(actualAllJobPosting.getNext());
        assertEquals("ok", actualAllJobPosting.getMessage());
        assertEquals(2, ((Collection<JobPostingResponseList>) actualAllJobPosting.getData()).size());
        verify(modelMapper, atLeast(1)).map((Object) any(), (Class<JobPostingResponseList>) any());
        verify(pengajuanSDMRepository).findByPosisiContainingIgnoreCaseAndStatus((String) any(), (Short) any(),
                (Pageable) any());
    }

    /**
     * Method under test: {@link JobPostingService#getJobById(Long)}
     */
    @Test
    void testGetJobById2() {
        when(modelMapper.map((Object) any(), (Class<JobPostingDetailResponse>) any()))
                .thenThrow(new ValidationErrorException("An error occurred"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);

        PengajuanSDMEntity pengajuanSDMEntity = new PengajuanSDMEntity();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setDeadline(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setDescription("The characteristics of someone or something");
        pengajuanSDMEntity.setIdPengajuan(1L);
        pengajuanSDMEntity.setNumberApplicant(10);
        pengajuanSDMEntity.setNumberRequired(10);
        pengajuanSDMEntity.setPosisi("Posisi");
        pengajuanSDMEntity.setRemarkHR("Remark staff");
        pengajuanSDMEntity.setStatus((short) 1);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setUser(user);
        Optional<PengajuanSDMEntity> ofResult = Optional.of(pengajuanSDMEntity);
        when(pengajuanSDMRepository.findByIdPengajuanAndStatus((Long) any(), anyShort())).thenReturn(ofResult);
        assertThrows(ValidationErrorException.class, () -> jobPostingService.getJobById(123L));
        verify(modelMapper).map((Object) any(), (Class<JobPostingDetailResponse>) any());
        verify(pengajuanSDMRepository).findByIdPengajuanAndStatus((Long) any(), anyShort());
    }

    /**
     * Method under test: {@link JobPostingService#getApplicantJobPosting(int, int, StatusRecruitment, Long)}
     */
    @Test
    void testGetApplicantJobPosting() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);

        PengajuanSDMEntity pengajuanSDMEntity = new PengajuanSDMEntity();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setDeadline(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setDescription("The characteristics of someone or something");
        pengajuanSDMEntity.setIdPengajuan(1L);
        pengajuanSDMEntity.setNumberApplicant(10);
        pengajuanSDMEntity.setNumberRequired(10);
        pengajuanSDMEntity.setPosisi("Posisi");
        pengajuanSDMEntity.setRemarkHR("Remark HR");
        pengajuanSDMEntity.setRemarkStaff("Remark Staff");
        pengajuanSDMEntity.setStatus((short) 1);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setUser(user);
        Optional<PengajuanSDMEntity> ofResult = Optional.of(pengajuanSDMEntity);
        when(pengajuanSDMRepository.findById((Long) any())).thenReturn(ofResult);
        when(submissionRepository.findByJobPostingAndStatus((PengajuanSDMEntity) any(), (StatusRecruitment) any(),
                (Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        PageableResponse actualApplicantJobPosting = jobPostingService.getApplicantJobPosting(1, 3,
                StatusRecruitment.APPLIED, 123L);
        assertEquals(1, actualApplicantJobPosting.getCurrentPage().intValue());
        assertEquals(1, actualApplicantJobPosting.getTotalPages().intValue());
        assertEquals(0L, actualApplicantJobPosting.getTotalData().longValue());
        assertFalse(actualApplicantJobPosting.getPrevious());
        assertEquals(0, actualApplicantJobPosting.getPageSize().intValue());
        assertFalse(actualApplicantJobPosting.getNext());
        assertTrue(((Collection<Object>) actualApplicantJobPosting.getData()).isEmpty());
        verify(pengajuanSDMRepository).findById((Long) any());
        verify(submissionRepository).findByJobPostingAndStatus((PengajuanSDMEntity) any(), (StatusRecruitment) any(),
                (Pageable) any());
    }

    /**
     * Method under test: {@link JobPostingService#getApplicantJobPosting(int, int, StatusRecruitment, Long)}
     */
    @Test
    void testGetApplicantJobPosting2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);

        PengajuanSDMEntity pengajuanSDMEntity = new PengajuanSDMEntity();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setDeadline(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setDescription("The characteristics of someone or something");
        pengajuanSDMEntity.setIdPengajuan(1L);
        pengajuanSDMEntity.setNumberApplicant(10);
        pengajuanSDMEntity.setNumberRequired(10);
        pengajuanSDMEntity.setPosisi("Posisi");
        pengajuanSDMEntity.setRemarkHR("Remark HR");
        pengajuanSDMEntity.setRemarkStaff("Remark Staff");
        pengajuanSDMEntity.setStatus((short) 1);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setUser(user);
        Optional<PengajuanSDMEntity> ofResult = Optional.of(pengajuanSDMEntity);
        when(pengajuanSDMRepository.findById((Long) any())).thenReturn(ofResult);
        when(submissionRepository.findByJobPostingAndStatus((PengajuanSDMEntity) any(), (StatusRecruitment) any(),
                (Pageable) any())).thenThrow(new ValidationErrorException("An error occurred"));
        assertThrows(ValidationErrorException.class,
                () -> jobPostingService.getApplicantJobPosting(1, 3, StatusRecruitment.APPLIED, 123L));
        verify(pengajuanSDMRepository).findById((Long) any());
        verify(submissionRepository).findByJobPostingAndStatus((PengajuanSDMEntity) any(), (StatusRecruitment) any(),
                (Pageable) any());
    }

    /**
     * Method under test: {@link JobPostingService#getApplicantJobPosting(int, int, StatusRecruitment, Long)}
     */
    @Test
    void testGetApplicantJobPosting4() {
        when(pengajuanSDMRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(submissionRepository.findByJobPostingAndStatus((PengajuanSDMEntity) any(), (StatusRecruitment) any(),
                (Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertThrows(DataNotFoundException.class,
                () -> jobPostingService.getApplicantJobPosting(1, 3, StatusRecruitment.APPLIED, 123L));
        verify(pengajuanSDMRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link JobPostingService#getApplicantJobPosting(int, int, StatusRecruitment, Long)}
     */
    @Test
    void testGetApplicantJobPosting5() {
        JobAppliedListResponse jobAppliedListResponse = new JobAppliedListResponse();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        jobAppliedListResponse.setAppliedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        jobAppliedListResponse.setCoverLetter("Cover Letter");
        jobAppliedListResponse.setDescription("The characteristics of someone or something");
        jobAppliedListResponse.setStatus(StatusRecruitment.APPLIED);
        jobAppliedListResponse.setSubmissionId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        jobAppliedListResponse.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(modelMapper.map((Object) any(), (Class<JobAppliedListResponse>) any())).thenReturn(jobAppliedListResponse);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);

        PengajuanSDMEntity pengajuanSDMEntity = new PengajuanSDMEntity();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setDeadline(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setDescription("The characteristics of someone or something");
        pengajuanSDMEntity.setIdPengajuan(1L);
        pengajuanSDMEntity.setNumberApplicant(10);
        pengajuanSDMEntity.setNumberRequired(10);
        pengajuanSDMEntity.setPosisi("Posisi");
        pengajuanSDMEntity.setRemarkHR("Remark HR");
        pengajuanSDMEntity.setRemarkStaff("Remark Staff");
        pengajuanSDMEntity.setStatus((short) 1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setUpdatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setUser(user);
        Optional<PengajuanSDMEntity> ofResult = Optional.of(pengajuanSDMEntity);
        when(pengajuanSDMRepository.findById((Long) any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setName("appliedAt");
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRole(RoleName.USER);
        user1.setUserId(123L);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setJoinedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setLastLogin(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        user2.setName("appliedAt");
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("4105551212");
        user2.setRole(RoleName.USER);
        user2.setUserId(123L);

        PengajuanSDMEntity pengajuanSDMEntity1 = new PengajuanSDMEntity();
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity1.setCreatedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity1.setDeadline(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity1.setDescription("The characteristics of someone or something");
        pengajuanSDMEntity1.setIdPengajuan(1L);
        pengajuanSDMEntity1.setNumberApplicant(10);
        pengajuanSDMEntity1.setNumberRequired(10);
        pengajuanSDMEntity1.setPosisi("appliedAt");
        pengajuanSDMEntity1.setRemarkHR("appliedAt");
        pengajuanSDMEntity1.setRemarkStaff("appliedAt");
        pengajuanSDMEntity1.setStatus((short) 1);
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity1.setUpdatedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity1.setUser(user2);

        Submission submission = new Submission();
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        submission.setAppliedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        submission.setAppliedBy(user1);
        submission.setCoverLetter("appliedAt");
        submission.setDescription("The characteristics of someone or something");
        submission.setJobPosting(pengajuanSDMEntity1);
        submission.setStatus(StatusRecruitment.APPLIED);
        submission.setSubmissionId(123L);

        ArrayList<Submission> submissionList = new ArrayList<>();
        submissionList.add(submission);
        PageImpl<Submission> pageImpl = new PageImpl<>(submissionList);
        when(submissionRepository.findByJobPostingAndStatus((PengajuanSDMEntity) any(), (StatusRecruitment) any(),
                (Pageable) any())).thenReturn(pageImpl);
        PageableResponse actualApplicantJobPosting = jobPostingService.getApplicantJobPosting(1, 3,
                StatusRecruitment.APPLIED, 123L);
        assertEquals(1, actualApplicantJobPosting.getCurrentPage().intValue());
        assertEquals(1, actualApplicantJobPosting.getTotalPages().intValue());
        assertEquals(1L, actualApplicantJobPosting.getTotalData().longValue());
        assertFalse(actualApplicantJobPosting.getPrevious());
        assertEquals(1, actualApplicantJobPosting.getPageSize().intValue());
        assertFalse(actualApplicantJobPosting.getNext());
        assertEquals("ok", actualApplicantJobPosting.getMessage());
        assertEquals(1, ((Collection<JobAppliedListResponse>) actualApplicantJobPosting.getData()).size());
        verify(modelMapper).map((Object) any(), (Class<JobAppliedListResponse>) any());
        verify(pengajuanSDMRepository).findById((Long) any());
        verify(submissionRepository).findByJobPostingAndStatus((PengajuanSDMEntity) any(), (StatusRecruitment) any(),
                (Pageable) any());
    }

    /**
     * Method under test: {@link JobPostingService#setStatus(Long, StatusJobApplicantRequest)}
     */
    @Test
    void testSetStatus() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRole(RoleName.USER);
        user1.setUserId(123L);

        PengajuanSDMEntity pengajuanSDMEntity = new PengajuanSDMEntity();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setDeadline(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setDescription("The characteristics of someone or something");
        pengajuanSDMEntity.setIdPengajuan(1L);
        pengajuanSDMEntity.setNumberApplicant(10);
        pengajuanSDMEntity.setNumberRequired(10);
        pengajuanSDMEntity.setPosisi("Posisi");
        pengajuanSDMEntity.setRemarkHR("Remark HR");
        pengajuanSDMEntity.setRemarkStaff("Remark Staff");
        pengajuanSDMEntity.setStatus((short) 1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        pengajuanSDMEntity.setUpdatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        pengajuanSDMEntity.setUser(user1);

        Submission submission = new Submission();
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        submission.setAppliedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        submission.setAppliedBy(user);
        submission.setCoverLetter("Cover Letter");
        submission.setDescription("The characteristics of someone or something");
        submission.setJobPosting(pengajuanSDMEntity);
        submission.setStatus(StatusRecruitment.APPLIED);
        submission.setSubmissionId(123L);
        Optional<Submission> ofResult = Optional.of(submission);
        when(submissionRepository.findById((Long) any())).thenReturn(ofResult);

        StatusJobApplicantRequest statusJobApplicantRequest = new StatusJobApplicantRequest();
        statusJobApplicantRequest.setDescription("The characteristics of someone or something");
        statusJobApplicantRequest.setStatus(StatusRecruitment.APPLIED);
        assertThrows(ValidationErrorException.class, () -> jobPostingService.setStatus(123L, statusJobApplicantRequest));
        verify(submissionRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link JobPostingService#getSummary()}
     */
    @Test
    void testGetSummary() {
        when(pengajuanSDMRepository.findByStatus((Short) any())).thenReturn(new HashSet<>());
        when(submissionRepository.findByStatus((StatusRecruitment) any())).thenReturn(new HashSet<>());
        DashboardSummaryResponse actualSummary = jobPostingService.getSummary();
        assertEquals(0, actualSummary.getTotalApplied().intValue());
        assertEquals(0, actualSummary.getTotalJobRequest().intValue());
        assertEquals(0, actualSummary.getTotalJobPosting().intValue());
        verify(pengajuanSDMRepository, atLeast(1)).findByStatus((Short) any());
        verify(submissionRepository).findByStatus((StatusRecruitment) any());
    }

    /**
     * Method under test: {@link JobPostingService#getSummary()}
     */
    @Test
    void testGetSummary2() {
        when(pengajuanSDMRepository.findByStatus((Short) any())).thenReturn(new HashSet<>());
        when(submissionRepository.findByStatus((StatusRecruitment) any()))
                .thenThrow(new ValidationErrorException("An error occurred"));
        assertThrows(ValidationErrorException.class, () -> jobPostingService.getSummary());
        verify(pengajuanSDMRepository, atLeast(1)).findByStatus((Short) any());
        verify(submissionRepository).findByStatus((StatusRecruitment) any());
    }

}

