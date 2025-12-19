package com.benjorb.Tippapp.Service;

import com.benjorb.Tippapp.Dto.MatchWithTipps;
import com.benjorb.Tippapp.Dto.Team;
import com.benjorb.Tippapp.Dto.UserTippDto;
import com.benjorb.Tippapp.Model.Match;
import com.benjorb.Tippapp.Model.Teams;
import com.benjorb.Tippapp.Model.Tipps;
import com.benjorb.Tippapp.Repo.TeamRepo;
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
