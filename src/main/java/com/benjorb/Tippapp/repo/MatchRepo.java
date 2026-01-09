package com.benjorb.Tippapp.repo;

import com.benjorb.Tippapp.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepo extends JpaRepository<Match, Integer> {
    Optional<Match> findByMatchDayAndHomeTeamAndAwayTeam(int matchDay, String homeTeam, String awayTeam);

    List<Match> findByMatchDay(int matchDay);

    Optional<Match> findById(int id);

}
