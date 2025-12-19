package com.benjorb.Tippapp.Controller;

import com.benjorb.Tippapp.Dto.OddRequest;
import com.benjorb.Tippapp.Model.Match;
import com.benjorb.Tippapp.Model.Odds;
import com.benjorb.Tippapp.Service.MatchService;
import com.benjorb.Tippapp.Service.OddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api")
public class OddController {

    @Autowired
    OddService service;
    @Autowired
    MatchService matchService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/newOdds")
    public ResponseEntity<String> addOdds(@RequestBody OddRequest oddRequest){
        service.submittingOdds(
                oddRequest.getHomeWin(),
                oddRequest.getDraw(),
                oddRequest.getAwayWin(),
                oddRequest.getMatchId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("Quoten erfolgreich hinzugefügt/aktualisiert");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/newOdds/batch")
    public ResponseEntity<String> addMultipleOdds(@RequestBody List<OddRequest> oddsRequests){
        oddsRequests.forEach(oddRequest -> {
            service.submittingOdds(
                    oddRequest.getHomeWin(),
                    oddRequest.getDraw(),
                    oddRequest.getAwayWin(),
                    oddRequest.getMatchId()
            );
        });
        return ResponseEntity.status(HttpStatus.CREATED).body("Alle Quoten erfolgreich gespeichert");
    }

    // Quoten zu einem Match laden
    @GetMapping("/getOdds/{matchId}")
    public ResponseEntity<Odds> getOddsForMatch(@PathVariable int matchId) {
        Optional<Odds> odds = service.findByMatchId(matchId);
        return ResponseEntity.ok(odds.orElse(new Odds(1.0, 1.0, 1.0)));
    }
    @GetMapping("/odds/gameday/{matchday}")
    public ResponseEntity<List<Odds>> getOddsForMatchday(@PathVariable int matchday) {
        List<Match> matches = matchService.getMatchesForMatchday(matchday);
        List<Odds> oddsList = matches.stream()
                .map(m -> service.findByMatchId(m.getId()).orElse(new Odds(1.0, 1.0, 1.0)))
                .toList();

        return ResponseEntity.ok(oddsList);
    }



}
