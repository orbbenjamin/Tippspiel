package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.model.Users;
import com.benjorb.Tippapp.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    void registerTest(){

        Users user= new Users();
        String rawPW = "Hello123";

        user.setPassword(rawPW);

        Users savedUser = userService.register(user);
        assertNotEquals(rawPW, savedUser.getPassword());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        assertTrue(bCryptPasswordEncoder.matches(rawPW, savedUser.getPassword()));

        assertEquals("USER", savedUser.getRole());

        verify(userRepo).save(user);

    }
    @Test
    void verifyTest(){
        Users user = new Users();
        user.setUsername("Joe");
        user.setPassword("123");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken("Joe")).thenReturn("jwt-token");

        String result = userService.verify(user);
        assertEquals("jwt-token", result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken("Joe");

    }
    @Test
    void findByUsernameTest(){
        Users user = new Users();
        user.setUsername("Peter");

        when(userRepo.findByUsername("Peter")).thenReturn(user);
        Users foundUser = userService.findByUsername("Peter");

        assertEquals(user, foundUser);
        verify(userRepo).findByUsername("Peter");

    }
    @Test
    void findByIdTest(){
        int userId=333;
        Users currentUser = new Users();
        currentUser.setId(userId);

        Users repoUser = new Users();
        repoUser.setId(userId);

        UserService spyService = spy(userService);

        doReturn(currentUser).when(spyService).getCurrentUser();
        when(userRepo.findById(userId)).thenReturn(repoUser);

        Users foundUser = spyService.findById(userId);

        assertEquals(repoUser, foundUser);
        verify(userRepo).findById(userId);

    }
    @Test
    void findByIdForAdminTest(){
        int userId = 333;
        Users rightUser = new Users();
        rightUser.setRole("ADMIN");

        Users aimUser = new Users();
        aimUser.setRole("USER");
        aimUser.setId(userId);

        UserService spyService = spy(userService);
        doReturn(rightUser).when(spyService).getCurrentUser();

        when(userRepo.findById(userId)).thenReturn(aimUser);

        Users result = spyService.findByIdForAdmin(userId);
        assertEquals(aimUser, result);
        verify(userRepo).findById(userId);



    }
    @Test
    void deleteTest(){
        int userId = 333;
        Users currentUser = new Users();
        currentUser.setId(userId);
        Users toDelete = new Users();
        toDelete.setId(userId);

        UserService spyService = spy(userService);
        doReturn(currentUser).when(spyService).getCurrentUser();
        when(userRepo.findById(userId)).thenReturn(toDelete);

        String result = spyService.delete(userId);
        assertEquals("User Deleted", result);

        verify(userRepo).delete(toDelete);


    }
    @Test
    void deleteByAdminTest(){
        int userId = 333;
        Users rightUser = new Users();
        rightUser.setRole("ADMIN");

        Users toDelete = new Users();
        toDelete.setRole("USER");
        toDelete.setId(userId);

        UserService spyService = spy(userService);
        doReturn(rightUser).when(spyService).getCurrentUser();

        when(userRepo.findById(userId)).thenReturn(toDelete);

        String result = spyService.deleteByAdmin(userId);
        assertEquals("User Deleted", result);
        verify(userRepo).delete(toDelete);

    }



}
