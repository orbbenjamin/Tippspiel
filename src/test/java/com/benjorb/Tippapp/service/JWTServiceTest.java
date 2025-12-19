package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.Service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JWTService jwtService;


    @BeforeEach
    void setUp(){
        jwtService = new JWTService();
    }

    @Test
    void generateAndExtract(){
        String username = "Joe";
        String token = jwtService.generateToken("Joe");

        String extractedUsername = jwtService.extractUserName(token);

        assertThat(extractedUsername).isEqualTo(username);
    }
    @Test
    void validShouldReturnTrueTest(){
        String username = "Joe";
        String token = jwtService.generateToken("Joe");
        when(userDetails.getUsername()).thenReturn(username);
        boolean isValid = jwtService.validateToken(token, userDetails);
        assertThat(isValid).isTrue();


    }
    @Test
    void invalidShouldReturnFalseTest(){
        String username = "Joe";
        String token = jwtService.generateToken("Other");
        when(userDetails.getUsername()).thenReturn(username);
        boolean isValid = jwtService.validateToken(token, userDetails);
        assertThat(isValid).isFalse();


    }


}
