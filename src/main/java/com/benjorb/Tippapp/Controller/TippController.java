package com.benjorb.Tippapp.Controller;

import com.benjorb.Tippapp.Dto.*;
import com.benjorb.Tippapp.Model.Match;
import com.benjorb.Tippapp.Model.Tipps;
import com.benjorb.Tippapp.Model.Users;
import com.benjorb.Tippapp.Service.MatchService;
import com.benjorb.Tippapp.Service.TippService;
import com.benjorb.Tippapp.Service.UserService;
import com.benjorb.Tippapp.Service.UserTippListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TippController {

    @Autowired
    TippService tippService;
    @Autowired
    UserService userService;
    @Autowired
    MatchService matchService;
    @Autowired
    UserTippListService userTippListService;

    @PostMapping("/submit")
    public ResponseEntity<String> createTipp(@RequestBody TippRequest tippRequest) {
        Users user = userService.getCurrentUser();
        Match match = matchService.findById(tippRequest.getMatchId());
        tippService.sumbittingTipp(user, match, tippRequest.getHome(), tippRequest.getAway());
        return ResponseEntity.status(HttpStatus.CREATED).body("Erfolgreich getippt");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTipp(@RequestBody TippRequest tippRequest) {
        Users user = userService.getCurrentUser();
        Match match = matchService.findById(tippRequest.getMatchId());
        tippService.sumbittingTipp(user, match, tippRequest.getHome(), tippRequest.getAway());
        return ResponseEntity.ok("Erfolgreich aktualisiert");
    }

    @DeleteMapping("/delete/{matchId}")
    public ResponseEntity<String> deleteTipp(@PathVariable int matchId) {
        Users user = userService.getCurrentUser();
        Match match = matchService.findById(matchId);
        tippService.delete(user, match);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allTipps")
    public ResponseEntity<List<Tipps>> getAllTipps() {
        List<Tipps> tipps = tippService.getAllTipps();
        return ResponseEntity.ok(tipps);
    }

    @GetMapping("/matchesWithTips")
    public ResponseEntity<List<MatchWithTipps>> getMatchesWithTips(@RequestParam int matchday) {
        return ResponseEntity.ok(userTippListService.getMatchesWithTips(matchday));
    }

    @PostMapping("/submit/submitAll")
    public ResponseEntity<String> submitAll(@RequestBody TippListRequest request) {
        Users user = userService.getCurrentUser();

        try {
            for (TippRequest tipp : request.getTipps()) {
                Match match = matchService.findById(tipp.getMatchId());
                tippService.sumbittingTipp(user, match, tipp.getHome(), tipp.getAway());
            }
            return ResponseEntity.ok("Erfolgreich getippt");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fehler");

        }
    }
    @GetMapping("/points/total")
    public List<UserTotalPoints> getTotalPointsPerUser() {
        return tippService.getTotalPointsPerUser();
    }
}









