package com.benjorb.Tippapp.repo;

import com.benjorb.Tippapp.model.Match;
import com.benjorb.Tippapp.model.Odds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OddRepo extends JpaRepository<Odds, Integer> {

    Optional<Odds>findByMatch(Match match);
    Optional<Odds> findByMatchId(int matchId);



}
