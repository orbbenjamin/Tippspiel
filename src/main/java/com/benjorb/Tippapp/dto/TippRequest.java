package com.benjorb.Tippapp.dto;


public class TippRequest {
    private int matchId;
    private int home;
    private int away;

    public int getMatchId() {
        return matchId;
    }
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getHome() {
        return home;
    }
    public void setHome(int home) {
        this.home = home;
    }

    public int getAway() {
        return away;
    }
    public void setAway(int away) {
        this.away = away;
    }
}
