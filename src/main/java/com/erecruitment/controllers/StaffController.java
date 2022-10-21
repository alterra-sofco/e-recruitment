package com.erecruitment.controllers;

import com.erecruitment.dtos.requests.StaffRequestDTO;
import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.ResponseGenerator;
import com.erecruitment.entities.Staff;
import com.erecruitment.repositories.UserRepository;
import com.erecruitment.services.interfaces.IStaffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class StaffController {

    @Autowired
    private IStaffService staffService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/staff")
    public ResponseEntity<CommonResponse<List<StaffRequestDTO>>> getAllStaff () {
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(
                String.valueOf(HttpStatus.OK.value()),
                "Staff List",
                staffService.findAllStaff().stream().map(data->modelMapper
                        .map(data,StaffRequestDTO.class))
                        .collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<CommonResponse<StaffRequestDTO>> getStaffById (@PathVariable("id") Long staffId) {
        Optional<Staff> staff = staffService.findById(staffId);

        if(staff.isPresent()) {
            Staff convStaff = staff.get();
            StaffRequestDTO response = modelMapper.map(convStaff, StaffRequestDTO.class);

            ResponseGenerator responseGenerator = new ResponseGenerator();
            return new ResponseEntity<>(responseGenerator.responseData(
                    String.valueOf(HttpStatus.OK.value()),
                    "Staff found",
                    response), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }

    @PostMapping("user/{userId}/staff")
    public ResponseEntity<CommonResponse<StaffRequestDTO>> addNewStaff(@PathVariable("userId") Long userId ,@RequestBody StaffRequestDTO staff){
        Staff request = modelMapper.map(staff,Staff.class);
        Staff newStaff = staffService.addStaff(userId,request);

        StaffRequestDTO response = modelMapper.map(newStaff,StaffRequestDTO.class);

        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(
                String.valueOf(HttpStatus.CREATED.value()),
                "new staff added", response ), HttpStatus.CREATED);
    }

    @PutMapping("/staff/{id}")
    public ResponseEntity<CommonResponse<StaffRequestDTO>> updateStaff (@RequestBody StaffRequestDTO staff, @PathVariable("id") Long staffId){
        Staff request = modelMapper.map(staff,Staff.class);
        Optional<Staff> newStaff = Optional.ofNullable(staffService.updateStaff(staffId, request));
        if(newStaff.isPresent()) {
            Staff convStaff = newStaff.get();
            StaffRequestDTO response = modelMapper.map(convStaff,StaffRequestDTO.class);

            ResponseGenerator responseGenerator = new ResponseGenerator();
            return new ResponseEntity<>(responseGenerator.responseData(
                    String.valueOf(HttpStatus.ACCEPTED.value()),
                    "update by staff by Id: "+staffId,
                    response), HttpStatus.ACCEPTED);
        } else {
             return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }

    @DeleteMapping("/staff/{id}")
    public ResponseEntity<CommonResponse<Staff>> deleteStaff (@PathVariable("id") Long staffId){
        staffService.deleteStaff(staffId);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(
                String.valueOf(HttpStatus.ACCEPTED.value()),
                "staff deleted",staffId), HttpStatus.ACCEPTED);
    }
}
