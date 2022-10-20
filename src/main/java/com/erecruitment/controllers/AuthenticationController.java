package com.erecruitment.controllers;

import com.erecruitment.dtos.requests.auth.UserRegisterRequestDTO;
import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.entities.User;
import com.erecruitment.services.auth.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequestDTO requestDTO){
        User user = modelMapper.map(requestDTO, User.class);

        return new ResponseEntity<>(userService.registration(user), HttpStatus.CREATED);
    }
}
