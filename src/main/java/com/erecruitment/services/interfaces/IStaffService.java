package com.erecruitment.services.interfaces;

import com.erecruitment.entities.Staff;

import java.util.List;
import java.util.Optional;

public interface IStaffService {
    List<Staff> findAllStaff();
    Optional<Staff> findById(Long staffId);
    Staff addStaff(Long userId,Staff staff);
    Staff updateStaff (Long staffId, Staff staff);
    void deleteStaff(Long staffId);

}
