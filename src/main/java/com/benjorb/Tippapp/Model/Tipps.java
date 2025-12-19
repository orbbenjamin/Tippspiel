package com.benjorb.Tippapp.Model;
import jakarta.persistence.*;


@Entity
@Table(uniqueConstraints =
        {@UniqueConstraint(columnNames = {"user_id", "match_id"})})
public class Tipps {


    @ManyToOne
    private Match match;

    @ManyToOne
    private Users user;

    @Id
    @GeneratedValue
    private int id;
    private int predictedHome;
    private int predictedAway;
    private double points;

    public Tipps() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getPredictedAway() {
        return predictedAway;
    }

    public void setPredictedAway(int predictedAway) {
        this.predictedAway = predictedAway;
    }

    public int getPredictedHome() {
        return predictedHome;
    }

    public void setPredictedHome(int predictedHome) {
        this.predictedHome = predictedHome;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
