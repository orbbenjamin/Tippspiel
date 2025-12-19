package com.benjorb.Tippapp.Controller;

import com.benjorb.Tippapp.Dto.UserResponseDto;
import com.benjorb.Tippapp.Model.Users;
import com.benjorb.Tippapp.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    @Test
    void testRegisterSuccess() {
        Users user = new Users(1, "Joe", "joe@joe.de", "USER");
        Mockito.when(userService.register(user)).thenReturn(user);

        ResponseEntity<Users> response = controller.register(user);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(user);
    }

    @Test
    void testRegisterFailure() {
        Users user = new Users(1, "Joe", "joe@joe.de", "USER");
        Mockito.when(userService.register(user)).thenReturn(null);

        ResponseEntity<Users> response = controller.register(user);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isNull();
    }
    @Test
    void loginTest() {
        Users user = new Users(1, "Joe", "joe@joe.de", "USER");
        String token = "abc";

        Mockito.when(userService.verify(user)).thenReturn(token);
        Mockito.when(userService.findByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<?> response = controller.login(user);


        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(token, body.get("token"));
        assertEquals(user.getId(), body.get("userId"));
        assertEquals(user.getRole(), body.get("role"));
    }
    @Test
    void loginFailureTest() {
        Users user = new Users(1, "Joe", "joe@joe.de", "USER");

        Mockito.when(userService.verify(user)).thenReturn(null);

        ResponseEntity<?> response = controller.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void getUserByIdSuccess(){
        Users user = new Users(1, "Joe", "joe@joe.de", "USER");
        Mockito.when(userService.findById(1)).thenReturn(user);

        ResponseEntity<UserResponseDto> response = controller.getUser(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertEquals("Joe", response.getBody().getUsername());

        Mockito.verify(userService).findById(1);
    }
    @Test
    void getUserByIdFail(){
        Users user = new Users(1, "Joe", "joe@joe.de", "USER");
        Mockito.when(userService.findById(1)).thenReturn(null);

        ResponseEntity<UserResponseDto> response = controller.getUser(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Mockito.verify(userService).findById(1);
    }






}

