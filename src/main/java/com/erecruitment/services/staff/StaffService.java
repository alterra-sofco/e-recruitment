package com.erecruitment.services.staff;

import com.erecruitment.entities.Staff;
import com.erecruitment.repositories.PengajuanSDMRepository;
import com.erecruitment.repositories.StaffRepository;
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
    public Staff addStaff(Staff staff) {
        Staff dataStaff = new Staff();
        dataStaff.setUserId(staff.getUserId());
        dataStaff.setDepartmentId(staff.getDepartmentId());

        staffRepository.save(dataStaff);
        return dataStaff;
    }

    @Override
    public Optional<Staff> updateStaff(Long staffId, Staff staff) {
        Optional<Staff> dataStaff = staffRepository.findById(staffId);
        if (dataStaff.isPresent()) {
            dataStaff.get().setUserId(staff.getUserId());
            dataStaff.get().setDepartmentId(staff.getDepartmentId());

            staffRepository.save(dataStaff.get());
        }
        return dataStaff;
    }

    @Override
    public void deleteStaff(Long staffId) {
        staffRepository.deleteById(staffId);
    }


}
