package com.erecruitment.services;

import com.erecruitment.dtos.requests.AddPengajuanSDMRequest;
import com.erecruitment.dtos.requests.UpdateStatusPengajuanSDMRequest;
import com.erecruitment.dtos.response.PengajuanSDMResponse;
import com.erecruitment.entities.PengajuanSDMEntity;
import com.erecruitment.entities.PengajuanSDMSkillEntity;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.PengajuanSDMRepository;
import com.erecruitment.repositories.PengajuanSDMSkillRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PengajuanSDMServiceTest {

    @Mock
    PengajuanSDMRepository pengajuanSDMRepository;

    @Mock
    PengajuanSDMSkillRepository pengajuanSDMSkillRepository;

    @MockBean
    ModelMapper modelMapper = spy(new ModelMapper());

    @InjectMocks
    PengajuanSDMService serviceUnderTest = spy(new PengajuanSDMService());

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestEmptyPosisi_whenAddNewData() {
        AddPengajuanSDMRequest request = new AddPengajuanSDMRequest();
        List<Long> listSkill = new ArrayList<>();
        listSkill.add(1L);
        listSkill.add(2L);
        request.setPosisi("");
        request.setDescription("test");
        request.setNumberRequired(9);
        request.setRemarkStaff("segera");
        request.setListSkill(listSkill);
        serviceUnderTest.saveData(request, 0L);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestEmptyDescription_whenAddNewData() {
        AddPengajuanSDMRequest request = new AddPengajuanSDMRequest();
        List<Long> listSkill = new ArrayList<>();
        listSkill.add(1L);
        listSkill.add(2L);
        request.setPosisi("Programmer");
        request.setDescription("");
        request.setNumberRequired(9);
        request.setListSkill(listSkill);
        request.setRemarkStaff("segera");
        serviceUnderTest.saveData(request, 0L);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestNumberRequiredIs0_whenAddNewData() {
        AddPengajuanSDMRequest request = new AddPengajuanSDMRequest();
        List<Long> listSkill = new ArrayList<>();
        listSkill.add(1L);
        listSkill.add(2L);
        request.setPosisi("Programmer");
        request.setDescription("Test");
        request.setNumberRequired(0);
        request.setListSkill(listSkill);
        request.setRemarkStaff("segera");
        serviceUnderTest.saveData(request, 0L);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestListSkillEmpty_whenAddNewData() {
        AddPengajuanSDMRequest request = new AddPengajuanSDMRequest();
        List<Long> listSkill = new ArrayList<>();
        request.setPosisi("Programmer");
        request.setDescription("Test");
        request.setNumberRequired(9);
        request.setListSkill(listSkill);
        request.setRemarkStaff("segera");
        serviceUnderTest.saveData(request, 0L);
    }

    @Test
    public void whenGivenId_shouldDeleteDAta_ifFound() {
        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(1L);
        when(pengajuanSDMRepository.findById(entity.getIdPengajuan())).thenReturn(Optional.of(entity));
        serviceUnderTest.removeOne(entity.getIdPengajuan());
        verify(pengajuanSDMRepository).deleteById(entity.getIdPengajuan());
    }

    @Test(expected = DataNotFoundException.class)
    public void should_throw_exception_when_data_doesnt_exist() {
        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(19L);
        when(pengajuanSDMRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        serviceUnderTest.removeOne(entity.getIdPengajuan());
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestIdIs0_updateStatus() {
        UpdateStatusPengajuanSDMRequest request = new UpdateStatusPengajuanSDMRequest();
        LocalDateTime deadline = LocalDate.of(2022, 10, 31).atStartOfDay();
        request.setDeadline(Date.from(deadline.atZone(ZoneId.of("UTC")).toInstant()));
        request.setStatus((short) 2);
        request.setRemarkHR("Test");
        serviceUnderTest.updateStatus(request, 0L);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestStatus_updateStatus() {
        UpdateStatusPengajuanSDMRequest request = new UpdateStatusPengajuanSDMRequest();
        LocalDateTime deadline = LocalDate.of(2022, 10, 31).atStartOfDay();
        request.setDeadline(Date.from(deadline.atZone(ZoneId.of("UTC")).toInstant()));
        request.setStatus((short) 4);
        request.setRemarkHR("Test");
        serviceUnderTest.updateStatus(request, 1L);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestStatus3AndEmptyDescription_updateStatus() {
        UpdateStatusPengajuanSDMRequest request = new UpdateStatusPengajuanSDMRequest();
        request.setDeadline(null);
        request.setStatus((short) 3);
        request.setRemarkHR("Test");
        serviceUnderTest.updateStatus(request, 1L);
    }

    @Test(expected = DataNotFoundException.class)
    public void givenInValidRequestIdNotFound_updateStatus() {
        UpdateStatusPengajuanSDMRequest request = new UpdateStatusPengajuanSDMRequest();
        LocalDateTime deadline = LocalDate.of(2022, 10, 31).atStartOfDay();
        request.setDeadline(Date.from(deadline.atZone(ZoneId.of("UTC")).toInstant()));
        request.setStatus((short) 3);
        request.setRemarkHR("Test");
        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(1L);
        when(pengajuanSDMRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        serviceUnderTest.updateStatus(request, entity.getIdPengajuan());
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestStatusNotMatch_updateStatus() {
        UpdateStatusPengajuanSDMRequest request = new UpdateStatusPengajuanSDMRequest();
        LocalDateTime deadline = LocalDate.of(2022, 10, 31).atStartOfDay();
        request.setDeadline(Date.from(deadline.atZone(ZoneId.of("UTC")).toInstant()));
        request.setStatus((short) 3);
        request.setRemarkHR("Test");
        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(1L);
        entity.setStatus((short) 2);
        when(pengajuanSDMRepository.findById(anyLong())).thenReturn(Optional.ofNullable(entity));
        serviceUnderTest.updateStatus(request, entity.getIdPengajuan());
    }

    @Test
    public void givenValidRequest_updateStatus() {
        UpdateStatusPengajuanSDMRequest request = new UpdateStatusPengajuanSDMRequest();
        LocalDateTime deadline = LocalDate.of(2022, 10, 31).atStartOfDay();
        request.setDeadline(Date.from(deadline.atZone(ZoneId.of("UTC")).toInstant()));
        request.setStatus((short) 3);
        request.setRemarkHR("Test");
        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(1L);
        entity.setStatus((short) 1);

        User user = new User();
        user.setUserId(1L);
        user.setName("admin");

        when(pengajuanSDMRepository.findById(anyLong())).thenReturn(Optional.ofNullable(entity));
        PengajuanSDMEntity entity1 = modelMapper.map(request, PengajuanSDMEntity.class);
        entity1.setUser(user);
        when(pengajuanSDMRepository.save(any(PengajuanSDMEntity.class)))
                .thenReturn(entity1);
        PengajuanSDMResponse response = serviceUnderTest.updateStatus(request, 1L);
        assertThat(response.getStatus()).isEqualTo(request.getStatus());
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestIdIs0_closeJobPosted() {
        serviceUnderTest.closeJobPosted(0L);
    }

    @Test(expected = DataNotFoundException.class)
    public void givenInValidRequestIdNotFound_closeJobPosted() {
        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(19L);
        when(pengajuanSDMRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        serviceUnderTest.closeJobPosted(entity.getIdPengajuan());
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestStatusNotMatch_closeJobPosted() {
        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(19L);
        entity.setStatus((short) 4);
        when(pengajuanSDMRepository.findById(anyLong())).thenReturn(Optional.ofNullable(entity));
        serviceUnderTest.closeJobPosted(entity.getIdPengajuan());
    }

    @Test
    public void givenValidData_closeJobPosted() {
        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(19L);
        entity.setStatus((short) 3);
        when(pengajuanSDMRepository.findById(anyLong())).thenReturn(Optional.ofNullable(entity));

        User user = new User();
        user.setUserId(1L);
        user.setName("admin");
        PengajuanSDMEntity entity1 = new PengajuanSDMEntity();
        entity1.setIdPengajuan(entity.getIdPengajuan());
        entity1.setStatus((short) 4);
        entity1.setUser(user);
        when(pengajuanSDMRepository.save(any(PengajuanSDMEntity.class)))
                .thenReturn(entity1);
        PengajuanSDMResponse response = serviceUnderTest.closeJobPosted(entity.getIdPengajuan());
        assertThat(response.getStatus()).isEqualTo(entity1.getStatus());
    }

    @Test(expected = DataNotFoundException.class)
    public void givenInValidRequestIdNotFound_getDetail() {
        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(19L);
        when(pengajuanSDMRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        serviceUnderTest.getDetail(entity.getIdPengajuan());
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestIdIs0_getDetail() {
        serviceUnderTest.getDetail(0L);
    }

    @Test
    public void givenValidRequest_getDetail() {
        PengajuanSDMSkillEntity pengajuanSDMSkillEntity = new PengajuanSDMSkillEntity();
        pengajuanSDMSkillEntity.setSkillName("JAVA");
        pengajuanSDMSkillEntity.setSkillId(1L);

        PengajuanSDMEntity entity = new PengajuanSDMEntity();
        entity.setIdPengajuan(1L);
        entity.setStatus((short) 1);

        User user = new User();
        user.setUserId(1L);
        user.setName("admin");
        entity.setUser(user);
        Set<PengajuanSDMSkillEntity> pengajuanSDMSkillEntity1 = pengajuanSDMSkillRepository.findByPengajuanId(entity.getIdPengajuan());
        pengajuanSDMSkillEntity1.add(pengajuanSDMSkillEntity);
        when(pengajuanSDMRepository.findById(anyLong())).thenReturn(Optional.ofNullable(entity));
        PengajuanSDMResponse response = serviceUnderTest.getDetail(1L);
        response.setListSkill(pengajuanSDMSkillEntity1);
        assertThat(response.getIdPengajuan()).isEqualTo(entity.getIdPengajuan());
    }


}
