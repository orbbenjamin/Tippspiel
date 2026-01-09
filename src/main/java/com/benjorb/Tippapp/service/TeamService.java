package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.model.Teams;
import com.benjorb.Tippapp.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepo repo;

    public List<Teams> getAllTeams(){
        return repo.findAll();
    }
}


