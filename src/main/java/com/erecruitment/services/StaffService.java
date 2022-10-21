package com.erecruitment.services;

import com.erecruitment.entities.Staff;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.repositories.PengajuanSDMRepository;
import com.erecruitment.repositories.StaffRepository;
import com.erecruitment.repositories.UserRepository;
import com.erecruitment.services.interfaces.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        return staffRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public Optional<Staff> findById(Long staffId) {
        return staffRepository.findById(staffId);
    }

    @Override
    public Staff addStaff(Long userId, Staff staff) {
        Staff dataStaff = new Staff();

        Optional<User> selectedUser = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("data not found")));
            if(selectedUser.isPresent()){
                User userConv = selectedUser.get();
                dataStaff.setUser(userConv); //object
                dataStaff.setDepartmentId(staff.getDepartmentId()); //id
                return staffRepository.save(dataStaff);
            } else {
                return null;
            }
    }

    @Override
    public Staff updateStaff(Long staffId, Staff staff) {
        Optional<Staff> dataStaff = Optional.ofNullable(staffRepository.findById(staffId)
                .orElseThrow(() -> new DataNotFoundException("data not found")));
            if(dataStaff.isPresent()) {
                Staff newStaff = dataStaff.get();
                newStaff.setUser(staff.getUser()); //objects
                newStaff.setDepartmentId(staff.getDepartmentId());

                return staffRepository.save(newStaff);
            } else {
                return null;
            }
    }

    @Override
    public void deleteStaff(Long staffId) {
        staffRepository.deleteById(staffId);
    }


}
