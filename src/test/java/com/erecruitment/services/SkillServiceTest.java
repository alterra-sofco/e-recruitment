package com.erecruitment.services;

import com.erecruitment.dtos.requests.SkillRequest;
import com.erecruitment.dtos.response.SkillResponse;
import com.erecruitment.entities.SkillEntity;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SkillServiceTest {

    @Mock
    SkillRepository skillRepository;

    ModelMapper modelMapper = spy(new ModelMapper());

    @InjectMocks
    SkillService serviceUnderTest = spy(new SkillService());

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidRequest_whenAddNewData() {
        SkillRequest request = new SkillRequest();
        request.setSkillName("JAVA");
        SkillEntity skillEntity = modelMapper.map(request, SkillEntity.class);
        when(skillRepository.save(any(SkillEntity.class)))
                .thenReturn(skillEntity);
        SkillResponse response = serviceUnderTest.saveData(request, 0l);
        assertThat(response.getSkillName()).isEqualTo(request.getSkillName());
    }

    @Test(expected = ValidationErrorException.class)
    public void givenInValidRequestEmptySkillName_whenAddNewData() {
        SkillRequest request = new SkillRequest();
        request.setSkillName("");
        serviceUnderTest.saveData(request,0l);
    }

    @Test(expected = DataNotFoundException.class)
    public void should_throw_exception_whenUpdateData_thanNotFound() {
        SkillRequest request = new SkillRequest();
        request.setSkillName("Java");
        serviceUnderTest.saveData(request, 1l);
    }

    @Test(expected = DataNotFoundException.class)
    public void should_throw_exception_when_data_doesnt_exist() {
        SkillEntity entity = new SkillEntity();
        entity.setSkillId(19L);
        when(skillRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        serviceUnderTest.removeOne(entity.getSkillId());
    }

    @Test
    public void whenGivenId_shouldDeleteDAta_ifFound() {
        SkillEntity entity = new SkillEntity();
        entity.setSkillId(1L);
        when(skillRepository.findById(entity.getSkillId())).thenReturn(Optional.of(entity));
        serviceUnderTest.removeOne(entity.getSkillId());
        verify(skillRepository).deleteById(entity.getSkillId());
    }
}
