package com.benjorb.Tippapp.Dto;

public class UserTippDto {
    private String username;
    private int tippedHome;
    private int tippedAway;
    private double points;

    public UserTippDto() {
    }

    public UserTippDto(double points, int tippedAway, int tippedHome, String username) {
        this.points = points;
        this.tippedAway = tippedAway;
        this.tippedHome = tippedHome;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getTippedAway() {
        return tippedAway;
    }

    public void setTippedAway(int tippedAway) {
        this.tippedAway = tippedAway;
    }

    public int getTippedHome() {
        return tippedHome;
    }

    public void setTippedHome(int tippedHome) {
        this.tippedHome = tippedHome;
    }
}
