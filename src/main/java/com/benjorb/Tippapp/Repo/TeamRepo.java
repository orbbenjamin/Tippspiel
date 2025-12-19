package com.benjorb.Tippapp.Repo;

import com.benjorb.Tippapp.Model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepo extends JpaRepository<Teams, Integer> {


}
