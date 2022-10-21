package com.erecruitment.services.auth;

import com.erecruitment.entities.RoleName;
import com.erecruitment.entities.User;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
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
        assertThrows(DataNotFoundException.class, () -> userService.loadUserByUsername("jane.doe@example.org"));
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
    void testRegistration() {
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

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRole(RoleName.USER);
        user1.setUserId(123L);
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findByEmail(any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setJoinedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setLastLogin(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("4105551212");
        user2.setRole(RoleName.USER);
        user2.setUserId(123L);
        assertThrows(ValidationErrorException.class, () -> userService.registration(user2));
        verify(userRepository).findByEmail(any());
    }

    /**
     * Method under test: {@link UserService#registration(User)}
     */
    @Test
    void testRegistration2() {
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
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRole(RoleName.USER);
        user1.setUserId(123L);
        assertSame(user, userService.registration(user1));
        verify(userRepository).save(any());
        verify(userRepository).findByEmail(any());
    }
}

