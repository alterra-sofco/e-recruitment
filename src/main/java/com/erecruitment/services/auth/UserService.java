package com.erecruitment.services.auth;

import com.erecruitment.entities.Applicant;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.ApplicantRepository;
import com.erecruitment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException(String.format("user with email '%s' not found", email)));
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("User not found with id : " + id)
        );

        return user;
    }

    public User registration(User user) {
        boolean userExist = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExist) {
            System.out.println("test");
            throw new ValidationErrorException(
                    String.format("User with email '%s' already exist", user.getEmail())
            );
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Applicant profile = new Applicant();
        profile.setOwnedBy(user);
        applicantRepository.save(profile);
        return userRepository.save(user);
    }


}
