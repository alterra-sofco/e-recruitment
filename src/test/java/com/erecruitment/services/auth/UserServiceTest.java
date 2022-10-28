package com.erecruitment.services.auth;

import com.erecruitment.entities.Applicant;
import com.erecruitment.entities.File;
import com.erecruitment.entities.RoleName;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.CredentialErrorException;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.ApplicantRepository;
import com.erecruitment.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private ApplicantRepository applicantRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(any())).thenReturn(ofResult);
        assertSame(user, userService.loadUserByUsername("jane.doe@example.org"));
        verify(userRepository).findByEmail(any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        assertThrows(CredentialErrorException.class, () -> userService.loadUserByUsername("jane.doe@example.org"));
        verify(userRepository).findByEmail(any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        when(userRepository.findByEmail(any())).thenThrow(new ValidationErrorException("An error occurred"));
        assertThrows(ValidationErrorException.class, () -> userService.loadUserByUsername("jane.doe@example.org"));
        verify(userRepository).findByEmail(any());
    }

    /**
     * Method under test: {@link UserService#loadUserById(Long)}
     */
    @Test
    void testLoadUserById() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(any())).thenReturn(ofResult);
        assertSame(user, userService.loadUserById(123L));
        verify(userRepository).findById(any());
    }

    /**
     * Method under test: {@link UserService#loadUserById(Long)}
     */
    @Test
    void testLoadUserById2() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> userService.loadUserById(123L));
        verify(userRepository).findById(any());
    }

    /**
     * Method under test: {@link UserService#loadUserById(Long)}
     */
    @Test
    void testLoadUserById3() {
        when(userRepository.findById(any())).thenThrow(new ValidationErrorException("An error occurred"));
        assertThrows(ValidationErrorException.class, () -> userService.loadUserById(123L));
        verify(userRepository).findById(any());
    }

    /**
     * Method under test: {@link UserService#registration(User)}
     */
    @Test
    void testRegistration() throws UnsupportedEncodingException {
        File file = new File();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        file.setData("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        file.setDeleted(true);
        file.setDisplayName("Display Name");
        file.setFileId(123L);
        file.setType("Type");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));

        File file1 = new File();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        file1.setData("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        file1.setDeleted(true);
        file1.setDisplayName("Display Name");
        file1.setFileId(123L);
        file1.setType("Type");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);

        Applicant applicant = new Applicant();
        applicant.setAddress("42 Main St");
        applicant.setApplicantId(123L);
        applicant.setAvatar(file);
        applicant.setBio("Bio");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        applicant.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        applicant.setCv(file1);
        applicant.setDeleted(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        applicant.setDob(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        applicant.setOwnedBy(user);
        applicant.setPortofolioURL("https://example.org/example");
        applicant.setSkills(new HashSet<>());
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        applicant.setUpdatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        when(applicantRepository.save(any())).thenReturn(applicant);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRole(RoleName.USER);
        user1.setUserId(123L);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setJoinedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setLastLogin(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("4105551212");
        user2.setRole(RoleName.USER);
        user2.setUserId(123L);
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.save(any())).thenReturn(user1);
        when(userRepository.findByEmail(any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user3.setJoinedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user3.setLastLogin(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        user3.setName("Name");
        user3.setPassword("iloveyou");
        user3.setPhoneNumber("4105551212");
        user3.setRole(RoleName.USER);
        user3.setUserId(123L);
        assertThrows(ValidationErrorException.class, () -> userService.registration(user3));
        verify(userRepository).findByEmail(any());
    }

    /**
     * Method under test: {@link UserService#registration(User)}
     */
    @Test
    void testRegistration2() throws UnsupportedEncodingException {
        File file = new File();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        file.setData("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        file.setDeleted(true);
        file.setDisplayName("Display Name");
        file.setFileId(123L);
        file.setType("Type");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));

        File file1 = new File();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        file1.setData("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        file1.setDeleted(true);
        file1.setDisplayName("Display Name");
        file1.setFileId(123L);
        file1.setType("Type");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        file1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRole(RoleName.USER);
        user.setUserId(123L);

        Applicant applicant = new Applicant();
        applicant.setAddress("42 Main St");
        applicant.setApplicantId(123L);
        applicant.setAvatar(file);
        applicant.setBio("Bio");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        applicant.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        applicant.setCv(file1);
        applicant.setDeleted(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        applicant.setDob(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        applicant.setOwnedBy(user);
        applicant.setPortofolioURL("https://example.org/example");
        applicant.setSkills(new HashSet<>());
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        applicant.setUpdatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        when(applicantRepository.save(any())).thenReturn(applicant);
        when(userRepository.save(any())).thenThrow(new UsernameNotFoundException("Msg"));
        when(userRepository.findByEmail(any())).thenThrow(new UsernameNotFoundException("Msg"));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRole(RoleName.USER);
        user1.setUserId(123L);
        assertThrows(UsernameNotFoundException.class, () -> userService.registration(user1));
        verify(userRepository).findByEmail(any());
    }
}

