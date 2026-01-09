package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.dto.MatchResponse;
import com.benjorb.Tippapp.dto.MatchDto;
import com.benjorb.Tippapp.model.Gameday;
import com.benjorb.Tippapp.model.Match;
import com.benjorb.Tippapp.repo.GamedayRepo;
import com.benjorb.Tippapp.repo.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MatchService {


    @Autowired
    MatchRepo repo;
    @Autowired
    FootballApiService service;
    @Autowired
    GamedayRepo gamedayRepo;


    public List<Match> getMatchesForMatchday(int matchDay){
        return repo.findByMatchDay(matchDay);
    }


    public int getCurrentMatchdayFromApi() {
        MatchResponse response = service.getMatches(0);
        if (response == null || response.getMatches() == null || response.getMatches().isEmpty()) {
            return -1;
        }

        return response
                .getMatches()
                .get(0)
                .getSeason()
                .getCurrentMatchday();
    }






    public Match findById(int id){
        return repo.findById(id).orElse(null);
    }

    public boolean canPlaceTip(MatchDto matchDto) {

        return matchDto.getStatus().equals("SCHEDULED");
    }


    public List<Match> getAllGames(){
        return repo.findAll();

    }
    //@PostConstruct
    public void loadAllMatchesForAllMatchdays() {
        int maxMatchday = 34;  // feste Anzahl der Spieltage

        for (int matchday = 1; matchday <= maxMatchday; matchday++) {
            System.out.println("Lade Spiele für Matchday " + matchday);
            MatchResponse response = service.getMatches(matchday);

            if (response.getMatches() == null || response.getMatches().isEmpty()) {
                System.out.println("Keine Spiele für Matchday " + matchday);
                continue;
            }

            for (MatchDto dto : response.getMatches()) {
                Match match = new Match();

                match.setId(dto.getId());  // API-ID als Primärschlüssel übernehmen
                match.setMatchDay(dto.getMatchday());
                match.setHomeTeam(dto.getHomeTeam().getName());
                match.setAwayTeam(dto.getAwayTeam().getName());
                match.setHomeScore(dto.getScore().getFullTime().getHome() != null ? dto.getScore().getFullTime().getHome() : 0);
                match.setAwayScore(dto.getScore().getFullTime().getAway() != null ? dto.getScore().getFullTime().getAway() : 0);
                match.setStatus(dto.getStatus());

                repo.save(match);
            }
        }
    }
   
    @Scheduled(cron = "0 0 3 * * *")
    public void updateMatchday() {
        int current = getCurrentMatchdayFromApi();
        if (current == -1) {
            System.out.println("Konnte aktuellen Spieltag nicht von der API laden.");
            return;
        }

        System.out.println("Aktualisiere aktuellen Spieltag: " + current);

        MatchResponse response = service.getMatches(current);

        if (response.getMatches() == null || response.getMatches().isEmpty()) {
            System.out.println("Keine Spiele für aktuellen Spieltag " + current);
            return;
        }

        for (MatchDto dto : response.getMatches()) {


            Match match = repo.findById(dto.getId()).orElse(new Match());

            // API-ID als Primary Key verwenden
            match.setId(dto.getId());
            match.setMatchDay(dto.getMatchday());
            match.setHomeTeam(dto.getHomeTeam().getName());
            match.setAwayTeam(dto.getAwayTeam().getName());
            match.setHomeScore(dto.getScore().getFullTime().getHome() != null ? dto.getScore().getFullTime().getHome() : 0);
            match.setAwayScore(dto.getScore().getFullTime().getAway() != null ? dto.getScore().getFullTime().getAway() : 0);
            match.setStatus(dto.getStatus());

            repo.save(match);

        }
        Gameday gameday = gamedayRepo.findById(1).orElse(new Gameday());
        gameday.setCurrentMatchday(current);
        gamedayRepo.save(gameday);
    }
    @Scheduled(cron = "0 */5 * * * *") //
    public void updateLiveMatches() {

        int current = getCurrentMatchdayFromApi();

        System.out.println("Live-Update für aktuellen Spieltag: " + current);

        updateMatchday();
    }
    public int getCurrentMatchday() {
        return gamedayRepo.findById(1)
                .map(Gameday::getCurrentMatchday)
                .orElse(-1);  // z.B. -1 falls nicht gesetzt
    }


    public List<Match> findAll() {
       return repo.findAll();
    }
    public List<Match>findAllWithCurrentMatchday(){
        int i = getCurrentMatchday();
        return repo.findByMatchDay(i);
    }
}
