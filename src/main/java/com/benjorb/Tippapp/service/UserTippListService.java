package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.dto.MatchWithTipps;
import com.benjorb.Tippapp.dto.Team;
import com.benjorb.Tippapp.dto.UserTippDto;
import com.benjorb.Tippapp.model.Match;
import com.benjorb.Tippapp.model.Teams;
import com.benjorb.Tippapp.model.Tipps;
import com.benjorb.Tippapp.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserTippListService {

    @Autowired
    MatchService matchService;
    @Autowired
    TippService tippService;
    @Autowired
    ScoringService scoringService;
    @Autowired
    TeamRepo teamRepo;

    public List<MatchWithTipps> getMatchesWithTips(int matchday) {
        List<Match> matches = matchService.findAll();

        List<Match> filteredMatch=matches.stream().filter(m->m.getMatchDay()== matchday).collect(Collectors.toList());
        List<Tipps> tipps = tippService.getAllTipps();

        Map<Integer, List<Tipps>> tippsByMatch = tipps.stream()
                .collect(Collectors.groupingBy(t -> t.getMatch().getId()));

        List<MatchWithTipps> result = new ArrayList<>();

        for (Match match : filteredMatch) {
            MatchWithTipps dto = new MatchWithTipps();
            dto.setMatchId(match.getId());
            dto.setResultHome(match.getHomeScore());
            dto.setResultAway(match.getAwayScore());
            dto.setStatus(match.getStatus());
            dto.setMatchday(match.getMatchDay());

            Teams home = teamRepo.findById(match.getHomeTeamId()).orElse(null);
            Teams away = teamRepo.findById(match.getAwayTeamId()).orElse(null);

            dto.setHomeTeam(toTeamDto(home));
            dto.setAwayTeam(toTeamDto(away));

            List<Tipps> matchTipps = tippsByMatch.getOrDefault(match.getId(), List.of());

            List<UserTippDto> tippsDto = matchTipps.stream().map(tipp -> {
                UserTippDto t = new UserTippDto();
                t.setUsername(tipp.getUser().getUsername());
                t.setTippedHome(tipp.getPredictedHome());
                t.setTippedAway(tipp.getPredictedAway());

                if (match.getStatus().equals("SCHEDULED") || match.getStatus().equals("TIMED")) {
                    t.setPoints(0);
                } else {
                    double points = scoringService.calculateAndSavePoints(match.getId(), tipp.getUser().getId());
                    t.setPoints(points);
                }

                return t;
            }).collect(Collectors.toList());

            dto.setTips(tippsDto);

            result.add(dto);
        }

        return result;
    }
    private Team toTeamDto(Teams entity) {
        Team dto = new Team();
        dto.setName(entity.getName());
        dto.setShortForm(entity.getShortForm());
        dto.setCrest(entity.getCrest());
        return dto;
    }
}
