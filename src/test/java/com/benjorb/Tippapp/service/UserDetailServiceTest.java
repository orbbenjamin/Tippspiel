package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.Model.Users;
import com.benjorb.Tippapp.Repo.UserRepo;
import com.benjorb.Tippapp.Service.MyUserDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    MyUserDetailService myUserDetailService;

    @Test
    void loadByUsernameTest(){

        Users user=new Users();
        user.setUsername("Joe");

        when(userRepo.findByUsername("Joe")).thenReturn(user);
        UserDetails result = myUserDetailService.loadUserByUsername("Joe");
        assertEquals("Joe", result.getUsername());
        assertNotNull(result);
        verify(userRepo).findByUsername("Joe");

    }
    @Test
    void loadByUsernameTestException(){


        when(userRepo.findByUsername("Joe")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, ()->myUserDetailService.loadUserByUsername("Joe"));

        verify(userRepo).findByUsername("Joe");



    }

}
