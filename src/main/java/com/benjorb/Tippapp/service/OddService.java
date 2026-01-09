package com.benjorb.Tippapp.service;


import com.benjorb.Tippapp.model.Match;
import com.benjorb.Tippapp.model.Odds;
import com.benjorb.Tippapp.repo.OddRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OddService {

    @Autowired
    OddRepo repo;
    @Autowired
    MatchService service;

    public void submittingOdds(Double homeWin, Double draw, Double awayWin, int matchId) {

        Match match = service.findById(matchId);


        Optional<Odds> existing = repo.findByMatch(match);

        if (existing.isPresent()) {
            Odds odds = existing.get();
            odds.setHomeWin(homeWin);
            odds.setDraw(draw);
            odds.setAwayWin(awayWin);
            repo.save(odds);
        } else {
            Odds odds = new Odds();
            odds.setHomeWin(homeWin);
            odds.setDraw(draw);
            odds.setAwayWin(awayWin);
            odds.setMatch(match);
            repo.save(odds);
        }
    }



    public Optional<Odds> findByMatchId(int matchId) {
        return repo.findByMatchId(matchId);
    }
}
