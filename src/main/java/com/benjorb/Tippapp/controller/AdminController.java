package com.benjorb.Tippapp.controller;

import com.benjorb.Tippapp.dto.UserResponseDto;
import com.benjorb.Tippapp.dto.UserUpdateDto;
import com.benjorb.Tippapp.model.Users;
import com.benjorb.Tippapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserResponseDto>> getAllDetails(){
        return ResponseEntity.ok(userService.getAllDetails());
    }
    @GetMapping("admin/users/{id}")
    public ResponseEntity<UserResponseDto>getUserByAdmin(@PathVariable int id){
        Users user = userService.findByIdForAdmin(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        UserResponseDto dto = new UserResponseDto(user);
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<Users>changeUser(@PathVariable int id, @RequestBody UserUpdateDto userUpdateDto){
        Users updated = userService.changeUserByAdmin(id, userUpdateDto);
        if(updated == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("admin/users/{id}")
    public ResponseEntity<String>deleteUser(@PathVariable int id){
        return ResponseEntity.ok(userService.deleteByAdmin(id));
    }
}
