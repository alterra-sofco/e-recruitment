package com.erecruitment.controllers;

import com.erecruitment.dtos.requests.DepartmentRequestDTO;
import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.ResponseGenerator;
import com.erecruitment.entities.Department;
import com.erecruitment.services.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<DepartmentRequestDTO>>> getAllDept () {

        ResponseGenerator<DepartmentRequestDTO> responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(
                String.valueOf(HttpStatus.OK.value()),"Department list",
                departmentService.findAllDepartment().stream().map(data->modelMapper
                                .map(data,DepartmentRequestDTO.class))
                        .collect(Collectors.toList())), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<DepartmentRequestDTO>> getStaffById (@PathVariable("id") Long deptId) {
        Optional<Department> dept = departmentService.findDepartmentById(deptId);

        if(dept.isPresent()) {
            Department convDept = dept.get();
            DepartmentRequestDTO response = modelMapper.map(convDept, DepartmentRequestDTO.class);

            ResponseGenerator<DepartmentRequestDTO> responseGenerator = new ResponseGenerator();
            return new ResponseEntity<>(responseGenerator.responseData(
                    String.valueOf(HttpStatus.OK.value()),
                    "Department found",
                    response), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }

    @PostMapping
    public ResponseEntity<CommonResponse<DepartmentRequestDTO>> addNewStaff(@RequestBody DepartmentRequestDTO dept){
        Department request = modelMapper.map(dept,Department.class);
        Department newDept = departmentService.addDepartment(request);

        DepartmentRequestDTO response = modelMapper.map(newDept,DepartmentRequestDTO.class);

        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(
                String.valueOf(HttpStatus.CREATED.value()),
                "new Department added", response ), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<DepartmentRequestDTO>> updateStaff (@RequestBody DepartmentRequestDTO dept, @PathVariable("id") Long deptId){
        Department request = modelMapper.map(dept,Department.class);
        Optional<Department> newDept = Optional.ofNullable(departmentService.updateDepartment(deptId, request));
        if(newDept.isPresent()) {
            Department convDept = newDept.get();
            DepartmentRequestDTO response = modelMapper.map(convDept,DepartmentRequestDTO.class);

            ResponseGenerator responseGenerator = new ResponseGenerator();
            return new ResponseEntity<>(responseGenerator.responseData(
                    String.valueOf(HttpStatus.ACCEPTED.value()),
                    "update by staff by Id: "+deptId,
                    response), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Department>> deleteStaff (@PathVariable("id") Long deptId){
        departmentService.deleteDepartment(deptId);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(
                String.valueOf(HttpStatus.ACCEPTED.value()),
                "department name deleted",deptId), HttpStatus.ACCEPTED);
    }
}
