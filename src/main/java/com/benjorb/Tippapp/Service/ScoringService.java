package com.benjorb.Tippapp.Service;

import com.benjorb.Tippapp.Model.Match;
import com.benjorb.Tippapp.Model.Odds;
import com.benjorb.Tippapp.Model.Tipps;
import com.benjorb.Tippapp.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoringService {


    @Autowired
    MatchService matchService;

    @Autowired
    UserService userService;

    @Autowired
    TippService tippService;

    @Autowired
    OddService oddService;



    public double calculatePoints(Match match, Tipps tipp){

        int finalHome = match.getHomeScore();
        int finalAway = match.getAwayScore();

        int userTippHome = tipp.getPredictedHome();
        int userTippAway = tipp.getPredictedAway();

        Odds odds = oddService.findByMatchId(match.getId())
                .orElse(new Odds(1.0, 1.0, 1.0));

        double oddsForTipp = getOddsForTipp(userTippHome, userTippAway, odds);

        double points;

        if(userTippHome == finalHome && userTippAway == finalAway){
            points = 5 * oddsForTipp;
        } else {
            boolean correctTendency =
                    (finalHome > finalAway && userTippHome > userTippAway) ||
                            (finalHome < finalAway && userTippHome < userTippAway) ||
                            (finalHome == finalAway && userTippHome == userTippAway);

            boolean correctDistance =
                    (finalHome - finalAway) == (userTippHome - userTippAway);

            if(correctDistance && correctTendency){
                points = 3 * oddsForTipp;
            } else if(correctTendency){
                points = 2 * oddsForTipp;
            } else {
                points = 0;
            }
        }

        return points;
    }
    public double calculateAndSavePoints(int matchId, int userId) {
        Match match = matchService.findById(matchId);

        Users user = userService.findByIdWithoutCheck(userId);

        Tipps tipp = tippService.findByUserAndMatch(user, match)
                .orElse(null);

        if (tipp == null) {
            return 0;
        }

        double points = calculatePoints(match, tipp); // deine Berechnung

        tipp.setPoints(points);
        tippService.save(tipp);

        return points;
    }




    private double getOddsForTipp(int homeGoals, int awayGoals, Odds odds) {
        if (homeGoals > awayGoals) {
            return odds.getHomeWin();
        } else if (homeGoals == awayGoals) {
            return odds.getDraw();
        } else {
            return odds.getAwayWin();
        }
    }


}

