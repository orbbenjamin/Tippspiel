package com.benjorb.Tippapp.repo;

import com.benjorb.Tippapp.dto.UserTotalPoints;
import com.benjorb.Tippapp.model.Match;
import com.benjorb.Tippapp.model.Tipps;
import com.benjorb.Tippapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TippRepo extends JpaRepository<Tipps, Integer> {
    Optional<Tipps>findByUserAndMatch(Users user, Match match);





    @Query("""
      SELECT new com.benjorb.Tippapp.Dto.UserTotalPoints(
          t.user.username,
          SUM(t.points)
      )
      FROM Tipps t
      GROUP BY t.user.username
      ORDER BY SUM(t.points) DESC
    """)
    List<UserTotalPoints> getTotalPointsPerUser();


}
