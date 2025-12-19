package com.benjorb.Tippapp.Repo;

import com.benjorb.Tippapp.Model.Gameday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamedayRepo extends JpaRepository<Gameday, Integer> {

}
