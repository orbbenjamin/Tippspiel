package com.benjorb.Tippapp.Controller;

import com.benjorb.Tippapp.Dto.UserResponseDto;
import com.benjorb.Tippapp.Dto.UserUpdateDto;
import com.benjorb.Tippapp.Model.Users;
import com.benjorb.Tippapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService service;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user){
        String token = service.verify(user);

        if (token == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        Users fullUser = service.findByUsername(user.getUsername());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "userId", fullUser.getId(),
                "role", fullUser.getRole()
        ));
    }


    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user) {
        Users savedUser = service.register(user);
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable int userId) {
        Users user = service.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserResponseDto dto = new UserResponseDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/user/change/{userId}")
    public Users changeData(@PathVariable int userId, @RequestBody UserUpdateDto dto){
        return service.changeUser(userId, dto);
    }
    @DeleteMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable int userId){
        return service.delete(userId);
    }

    @GetMapping("/home/{userId}")
    public ResponseEntity<String> home(@PathVariable int userId){
        Users user = service.findById(userId);
        if(user == null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User nicht gefunden");
        }
        return ResponseEntity.ok("Willkommen " + user.getUsername());
    }
    @GetMapping("user/allUsers")
    public ResponseEntity<List<String>>getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }
}