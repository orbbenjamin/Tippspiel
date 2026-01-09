package com.benjorb.Tippapp.dto;

public class UserTotalPoints {
    private String username;
    private Double totalPoints;


    public UserTotalPoints(String username, Double totalPoints) {
        this.username = username;
        this.totalPoints = totalPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }
}
