package com.erecruitment.controllers;

import com.erecruitment.configuration.security.JwtTokenProvider;
import com.erecruitment.dtos.requests.auth.UserLoginRequestDTO;
import com.erecruitment.dtos.requests.auth.UserRegisterRequestDTO;
import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.auth.JwtAuthenticationResponseDTO;
import com.erecruitment.entities.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequestDTO requestDTO){
        User user = modelMapper.map(requestDTO, User.class);

        return new ResponseEntity<>(userService.registration(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequestDTO loginRequest) {

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
        return ResponseEntity.ok(new JwtAuthenticationResponseDTO(jwt));
    }
}
