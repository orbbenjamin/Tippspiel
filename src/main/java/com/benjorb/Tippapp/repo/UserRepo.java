package com.benjorb.Tippapp.repo;

import com.benjorb.Tippapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);

    Users findById(int userId);

    @Query("SELECT u.username FROM Users u")
    List<String>findAllUsernames();
}
