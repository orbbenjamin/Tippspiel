package com.benjorb.Tippapp.Repo;

import com.benjorb.Tippapp.Model.Match;
import com.benjorb.Tippapp.Model.Odds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OddRepo extends JpaRepository<Odds, Integer> {

    Optional<Odds>findByMatch(Match match);
    Optional<Odds> findByMatchId(int matchId);



}
