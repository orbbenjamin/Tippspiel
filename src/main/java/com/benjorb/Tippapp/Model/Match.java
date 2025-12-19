package com.benjorb.Tippapp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Match {


    @Id
    private int id;
    private int matchDay;
    private String homeTeam;
    private int homeTeamId;
    private String awayTeam;
    private int awayTeamId;
    private int homeScore;
    private int awayScore;
    private String status;




    public Match() {
    }

    public Match(int id) {
        this.id=id;
    }

    public Match(int id, int awayScore, String status, int homeScore, String awayTeam, int homeTeamId, String homeTeam, int matchDay, int awayTeamId) {
        this.id = id;
        this.awayScore = awayScore;
        this.status = status;
        this.homeScore = homeScore;
        this.awayTeam = awayTeam;
        this.homeTeamId = homeTeamId;
        this.homeTeam = homeTeam;
        this.matchDay = matchDay;
        this.awayTeamId = awayTeamId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(int matchDay) {
        this.matchDay = matchDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(int homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public int getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(int awayTeamId) {
        this.awayTeamId = awayTeamId;
    }


}
