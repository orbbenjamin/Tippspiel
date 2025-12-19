package com.benjorb.Tippapp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Gameday {

    @Id
    private int id = 1;

    private int currentMatchday;

    public Gameday(int id, int currentMatchday) {
        this.id = id;
        this.currentMatchday = currentMatchday;
    }

    public Gameday() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentMatchday() {
        return currentMatchday;
    }

    public void setCurrentMatchday(int currentMatchday) {
        this.currentMatchday = currentMatchday;
    }
}
