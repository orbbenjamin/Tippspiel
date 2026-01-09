package com.benjorb.Tippapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Teams {
    @Id
    private int id;
    private String name;
    private String shortForm;
    private String crest;

    public Teams(String crest, String shortForm, String name, int id) {
        this.crest = crest;
        this.shortForm = shortForm;
        this.name = name;
        this.id = id;
    }

    public Teams() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortForm() {
        return shortForm;
    }

    public void setShortForm(String shortForm) {
        this.shortForm = shortForm;
    }

    public String getCrest() {
        return crest;
    }

    public void setCrest(String crest) {
        this.crest = crest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
