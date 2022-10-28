package com.erecruitment.services;

import com.erecruitment.entities.Department;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.repositories.DepartmentRepository;
import com.erecruitment.services.interfaces.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements IDepartmentService {

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
    public Department addDepartment(Department department) {
        Department newDept = new Department();
        newDept.setDepartmentName(department.getDepartmentName());
        return departmentRepository.save(newDept);
    }

    @Override
    public Department updateDepartment(Long departmentId, Department department) {
        Optional<Department> newDept = departmentRepository.findById(departmentId);
        if (newDept.isPresent()) {
            Department response = newDept.get();
            response.setDepartmentName(department.getDepartmentName());
            return departmentRepository.save(response);
        }
        throw new DataNotFoundException("Data with ID: " + departmentId + " not found");
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}
