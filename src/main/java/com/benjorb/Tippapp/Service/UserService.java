package com.benjorb.Tippapp.Service;

import com.benjorb.Tippapp.Dto.UserResponseDto;
import com.benjorb.Tippapp.Dto.UserUpdateDto;
import com.benjorb.Tippapp.Model.Users;
import com.benjorb.Tippapp.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        repo.save(user);
        return user;
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    public Users findByUsername(String username) {
        return repo.findByUsername(username);
    }


    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return findByUsername(username);
    }


    public Users findById(int userId) {
        Users current=getCurrentUser();
        if(current.getId() != userId){
            throw new RuntimeException("You are not allowed here! Please Login!");
        }

        return repo.findById(userId);

    }
    public Users findByIdForAdmin(int userId) {
        Users current = getCurrentUser();
        if (!current.getRole().equals("ADMIN")) {
            throw new RuntimeException("You are not allowed here! Admin only!");
        }
        return repo.findById(userId);
    }

    public Users findByIdWithoutCheck(int userId) {
        Users user = repo.findById(userId);
        if (user == null) {
            throw new RuntimeException("User nicht gefunden");
        }
        return user;
    }


    public Users changeUser(int userId, UserUpdateDto dto) {
        Users current = getCurrentUser();

        if(current.getId() != userId){
            throw new RuntimeException("You are not allowed to change that! Please Login!");
        }

        Users existing = repo.findById(userId);
        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            existing.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            existing.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(encoder.encode(dto.getPassword()));

        }
        return repo.save(existing);
    }
    public Users changeUserByAdmin(int userId, UserUpdateDto dto) {
        Users current = getCurrentUser();
        if (!current.getRole().equals("ADMIN")) {
            throw new RuntimeException("You are not allowed here! Admin only!");
        }

        Users existing = repo.findById(userId);
        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            existing.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            existing.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(encoder.encode(dto.getPassword()));

        }
        return repo.save(existing);
    }

    public String delete(int userId) {
        Users current = getCurrentUser();

        if (current.getId() != userId) {
            throw new RuntimeException("You cannot delete another user!");
        }
        Users user=repo.findById(userId);
        repo.delete(user);
        return "User Deleted";
    }
    public String deleteByAdmin(int userId) {
        Users current = getCurrentUser();

        if (!current.getRole().equals("ADMIN")) {
            throw new RuntimeException("You are not allowed here! Admin only!");
        }
        Users user=repo.findById(userId);
        repo.delete(user);
        return "User Deleted";
    }

    public List<String> getAllUsers() {
        return repo.findAllUsernames();
    }

    public List<UserResponseDto> getAllDetails() {
        return repo.findAll().stream().map(user -> {
            UserResponseDto dto = new UserResponseDto();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            return dto;
        }).collect(Collectors.toList());
    }
}
