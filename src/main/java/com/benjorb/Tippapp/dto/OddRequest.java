package com.benjorb.Tippapp.dto;

public class OddRequest {

    private Double homeWin;
    private Double draw;
    private Double awayWin;
    private int matchId;

    public Double getAwayWin() {
        return awayWin;
    }

    public void setAwayWin(Double awayWin) {
        this.awayWin = awayWin;
    }

    public Double getDraw() {
        return draw;
    }

    public void setDraw(Double draw) {
        this.draw = draw;
    }

    public Double getHomeWin() {
        return homeWin;
    }

    public void setHomeWin(Double homeWin) {
        this.homeWin = homeWin;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
}
