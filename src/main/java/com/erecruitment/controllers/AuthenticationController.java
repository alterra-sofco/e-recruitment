package com.erecruitment.controllers;

import com.erecruitment.configuration.security.JwtTokenProvider;
import com.erecruitment.dtos.requests.auth.UserLoginRequestDTO;
import com.erecruitment.dtos.requests.auth.UserRegisterRequestDTO;
import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.ResponseGenerator;
import com.erecruitment.dtos.response.auth.JwtAuthenticationResponseDTO;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.UserRepository;
import com.erecruitment.services.auth.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> registerUser(@Valid @RequestBody UserRegisterRequestDTO requestDTO, @ApiIgnore Errors errors) {

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                throw new ValidationErrorException(error.getDefaultMessage());
            }
        }

        User user = modelMapper.map(requestDTO, User.class);

        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.CREATED.value()),
                "Account success registered!",
                userService.registration(user)), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> authenticateUser(@Valid @RequestBody UserLoginRequestDTO loginRequest, @ApiIgnore Errors errors) {

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                throw new ValidationErrorException(error.getDefaultMessage());
            }
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        Timestamp currentDatetime = new Timestamp(System.currentTimeMillis());
        User user = (User) authentication.getPrincipal();
        user.setLastLogin(currentDatetime);
        userRepository.save(user);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.CREATED.value()),
                "Login success!",
                new JwtAuthenticationResponseDTO(jwt)), HttpStatus.CREATED);
    }
}
