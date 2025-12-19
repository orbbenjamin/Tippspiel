package com.benjorb.Tippapp.Service;

import com.benjorb.Tippapp.Dto.UserTotalPoints;
import com.benjorb.Tippapp.Model.Match;
import com.benjorb.Tippapp.Model.Tipps;
import com.benjorb.Tippapp.Model.Users;
import com.benjorb.Tippapp.Repo.TippRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TippService {

    @Autowired
    TippRepo repo;







    public Tipps sumbittingTipp(Users user, Match match, int predictedHome, int predictedAway){

        Optional<Tipps> existingTipp=repo.findByUserAndMatch(user, match);

        Tipps tipp;

        if(existingTipp.isPresent()){
            tipp=existingTipp.get();
        }
        else{
            tipp=new Tipps();
            tipp.setUser(user);
            tipp.setMatch(match);
        }
        tipp.setPredictedHome(predictedHome);
        tipp.setPredictedAway(predictedAway);

        return repo.save(tipp);

    }


    public void delete(Users user, Match match) {
        Optional<Tipps> tippOpt = repo.findByUserAndMatch(user, match);
        tippOpt.ifPresentOrElse(
                repo::delete,
                () -> { throw new EntityNotFoundException("Kein Tipp gefunden"); }
        );
    }

    public Optional<Tipps>findByUserAndMatch(Users user, Match match) {
       return repo.findByUserAndMatch(user, match);
    }

    public void save(Tipps tipp) {
        repo.save(tipp);
    }

    public List<Tipps> getAllTipps(){
        return repo.findAll();
    }



    public List<UserTotalPoints> getTotalPointsPerUser() {
        return repo.getTotalPointsPerUser();
    }




}
