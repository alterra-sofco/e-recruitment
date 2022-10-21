package com.erecruitment.services;

import com.erecruitment.entities.Department;
import com.erecruitment.entities.Staff;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.repositories.DepartmentRepository;
import com.erecruitment.repositories.StaffRepository;
import com.erecruitment.repositories.UserRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private UserRepository userRepository;
    @Autowired
    @InjectMocks
    private StaffService staffService;
    private Staff staff1;
    private Staff staff2;
    List<Staff> staffList;

    @BeforeEach

    public void setUp() {
        staffList = new ArrayList<>();
        staff1 = new Staff(1L,new User(),111L);
        staff2 = new Staff(2L,new User(),222L);
        staffList.add(staff1);
        staffList.add(staff2);
    }
    @AfterEach
    public void tearDown() {
        staff1 = staff2 = null;
        staffList = null;
    }

    @Test
     void givenIdTODelete_ThenShouldDeleteTheStaff(){

        staffService.deleteStaff(1L);
        verify(staffRepository,times(1)).deleteById(1L);
    }

    @Test
     void GivenGetAllStaff_ShouldReturnListOfAllStaff(){
        staffRepository.save(staff1);
        //stubbing mock to return specific data
        when(staffRepository.findAll()).thenReturn(staffList);
        List<Staff> data = staffService.findAllStaff();
        assertEquals(data,staffList);
        verify(staffRepository,times(1)).save(staff1);
        verify(staffRepository,times(1)).findAll();
    }

    @Test
     void givenIdThen_ShouldReturnStaffOfThatId() {
        Mockito.when(staffRepository.findById(1L)).thenReturn(Optional.ofNullable(staff1));
        assertEquals (staffService.findById(staff1.getStaffId()),Optional.ofNullable(staff1));
    }

    @Test
    void givenStaffToAdd_ShouldReturnAddedStaff() throws DataNotFoundException {
        //stubbing
        User newUser = new User();
        newUser.setUserId(22L);

        when(userRepository.findById(any())).thenReturn(Optional.of(newUser));
        when(staffRepository.save(any())).thenReturn(staff1);
        staffService.addStaff(22L,staff2);
        verify(staffRepository,times(1)).save(any());
    }

    @Test
     void givenStaffById_thenReturnUpdatedDataById() throws IOException {

        given(staffRepository.findById(1L)).willReturn(Optional.ofNullable(staff1));
        staffService.updateStaff(1L,new Staff(1L,new User(),33L));
        assertThat(staffRepository.findById(1L).get().getDepartmentId()).isEqualTo(33L);
    }
}
