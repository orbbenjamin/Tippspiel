package com.benjorb.Tippapp.Controller;

import com.benjorb.Tippapp.Model.Match;
import com.benjorb.Tippapp.Model.Teams;
import com.benjorb.Tippapp.Service.MatchService;
import com.benjorb.Tippapp.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MatchContoller {

    @Autowired
    MatchService service;
    @Autowired
    TeamService teamService;

    @GetMapping("games/all")
    public ResponseEntity<List<Match>> getAllGames(){
        List<Match> matches = service.getAllGames();
        if(matches.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(matches);

    }
    @GetMapping("games/{id}")
    public ResponseEntity<Match> getOneGame(@PathVariable int id){
        Match match = service.findById(id);
        if (match == null) {
            return ResponseEntity.notFound().build();  // 404 wenn nicht gefunden
        }
        return ResponseEntity.ok(match);
    }

    @GetMapping("games/gameday/{matchday}")
    public List<Match> getGameday(@PathVariable int matchday){
        return service.getMatchesForMatchday(matchday);

    }
    @GetMapping("games/gameday/current")
    public int getCurrentMatchday() {
        return service.getCurrentMatchday();
    }

    @GetMapping("teams")
    public ResponseEntity<List<Teams>> getAllTeams(){
        List<Teams> teamlist = teamService.getAllTeams();
        if(teamlist.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teamlist);
    }


}
