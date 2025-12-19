package com.benjorb.Tippapp.Dto;

import java.util.List;

public class MatchWithTipps {
    private int matchId;
    private Team homeTeam;
    private Team awayTeam;
    private Integer resultHome;
    private Integer resultAway;
    private String status;
    private List<UserTippDto> tips;
    private int matchday;

    public MatchWithTipps() {
    }

    public MatchWithTipps(int matchId, Integer resultAway, Integer resultHome, Team awayTeam, Team homeTeam, String status, List<UserTippDto> tips, int matchday) {
        this.matchId = matchId;
        this.resultAway = resultAway;
        this.resultHome = resultHome;
        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;
        this.status = status;
        this.tips = tips;
        this.matchday=matchday;

    }

    public List<UserTippDto> getTips() {
        return tips;
    }

    public void setTips(List<UserTippDto> tips) {
        this.tips = tips;
    }

    public Integer getResultHome() {
        return resultHome;
    }

    public void setResultHome(Integer resultHome) {
        this.resultHome = resultHome;
    }

    public Integer getResultAway() {
        return resultAway;
    }

    public void setResultAway(Integer resultAway) {
        this.resultAway = resultAway;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }
}
