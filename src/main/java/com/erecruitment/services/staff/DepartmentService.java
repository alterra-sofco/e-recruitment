package com.erecruitment.services.staff;

import com.erecruitment.entities.Department;
import com.erecruitment.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements IDepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAllDepartment() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> findDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId);
    }

    @Override
    public Department addStaff(Department department) {
        Department newDept = new Department();
        newDept.setDepartmentName(department.getDepartmentName());
        return departmentRepository.save(newDept);
    }

    @Override
    public Department updateStaff(Long departmentId, Department department) {
        Optional<Department> newDept = departmentRepository.findById(departmentId);
        if (newDept.isPresent()){
            Department response = newDept.get();
            response.setDepartmentName(department.getDepartmentName());
            return departmentRepository.save(response);
        }
        return null;
    }

    @Override
    public void deleteStaff(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}
