package com.erecruitment.services;

import com.erecruitment.entities.Department;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.repositories.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    List<Department> departmentList;
    @Mock
    private DepartmentRepository departmentRepository;
    @Autowired
    @InjectMocks
    private DepartmentService departmentService;
    private Department dept1;
    private Department dept2;

    @BeforeEach
    public void setUp() {
        departmentList = new ArrayList<>();
        dept1 = new Department(1L, "Recruiter");
        dept2 = new Department(2L, "HR");
        departmentList.add(dept1);
        departmentList.add(dept2);
    }

    @AfterEach
    public void tearDown() {
        dept1 = dept2 = null;
        departmentList = null;
    }

    @Test
    void GivenGetAllDepartment_ShouldReturnListOfAllDepartment() {
        departmentRepository.save(dept1);
        //stubbing mock to return specific data
        when(departmentRepository.findAll()).thenReturn(departmentList);
        List<Department> productList1 = departmentService.findAllDepartment();
        assertEquals(productList1, departmentList);
        Mockito.verify(departmentRepository, times(1)).save(dept1);
        Mockito.verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void givenIdThen_ShouldReturnDepartmentOfThatId() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable(dept1));
        assertEquals(departmentService.findDepartmentById(dept1.getDepartmentId()), Optional.ofNullable(dept1));
    }

    @Test
    void givenDepartmentToAdd_ShouldReturnAddedDepartment() throws DataNotFoundException {
        //stubbing
        when(departmentRepository.save(any())).thenReturn(dept1);
        departmentService.addDepartment(dept1);
        verify(departmentRepository, times(1)).save(any());
    }

    @Test
    void givenDepartmentById_thenReturnUpdatedDataById() throws IOException {
        given(departmentRepository.findById(1L)).willReturn(Optional.ofNullable(dept1));
        departmentService.updateDepartment(1L, new Department(1L, "update"));
        assertThat(departmentRepository.findById(1L).get().getDepartmentName()).isEqualTo("update");
    }

    @Test
    void givenIdTODelete_ThenShouldDeleteTheDepartment() {
        departmentService.deleteDepartment(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void should_throw_exception_whenUpdateData_thanNotFound() throws DataNotFoundException {
        when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        assertThrows(DataNotFoundException.class, () -> departmentService.updateDepartment(1L, new Department(1L, "update")));
    }
}
