package com.benjorb.Tippapp.repo;

import com.benjorb.Tippapp.model.Gameday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamedayRepo extends JpaRepository<Gameday, Integer> {

}
