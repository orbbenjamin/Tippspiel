package com.benjorb.Tippapp.Service;

import com.benjorb.Tippapp.Model.Teams;
import com.benjorb.Tippapp.Repo.TeamRepo;
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


