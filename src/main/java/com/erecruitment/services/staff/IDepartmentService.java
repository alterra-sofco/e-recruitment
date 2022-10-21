package com.erecruitment.services.staff;

import com.erecruitment.entities.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {

    List<Department> findAllDepartment ();
    Optional<Department> findDepartmentById (Long departmentId);
    Department addDepartment(Department department);
    Department updateDepartment (Long departmentId, Department department);
    void deleteDepartment(Long departmentId);
}
