package com.benjorb.Tippapp.repo;

import com.benjorb.Tippapp.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepo extends JpaRepository<Teams, Integer> {


}
