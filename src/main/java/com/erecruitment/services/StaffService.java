package com.erecruitment.services;

import com.erecruitment.entities.Staff;
import com.erecruitment.entities.User;
import com.erecruitment.repositories.PengajuanSDMRepository;
import com.erecruitment.repositories.StaffRepository;
import com.erecruitment.repositories.UserRepository;
import com.erecruitment.services.interfaces.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService implements IStaffService {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PengajuanSDMRepository pengajuanSDMRepository;

    @Override
    public List<Staff> findAllStaff() {
        //sorted based on input on controller
        return staffRepository.findAll();
    }

    @Override
    public Optional<Staff> findById(Long staffId) {
        return staffRepository.findById(staffId);
    }

    @Override
    public Staff addStaff(Long userId, Staff staff) {
        Staff dataStaff = new Staff();

        User selectedUser = userRepository.findById(userId).get();
        dataStaff.setUser(selectedUser); //object
        dataStaff.setDepartmentId(staff.getDepartmentId()); //id
        return staffRepository.save(dataStaff);
    }

    @Override
    public Staff updateStaff(Long staffId, Staff staff) {
        Staff dataStaff = staffRepository.findById(staffId).get();
        dataStaff.setUser(staff.getUser()); //objects
        dataStaff.setDepartmentId(staff.getDepartmentId());

        return staffRepository.save(dataStaff);
    }

    @Override
    public void deleteStaff(Long staffId) {
        staffRepository.deleteById(staffId);
    }


}
