package com.benjorb.Tippapp.model;

import jakarta.persistence.*;

@Entity
public class Odds {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Double homeWin;
    private Double draw;
    private Double awayWin;

    @OneToOne
    @JoinColumn(name = "match_id")
    private Match match;

    public Odds() {
    }

    public Odds(Double homeWin, Double draw, Double awayWin) {
        this.homeWin = homeWin;
        this.draw = draw;
        this.awayWin = awayWin;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
