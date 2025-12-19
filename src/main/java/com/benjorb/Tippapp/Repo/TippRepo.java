package com.benjorb.Tippapp.Repo;

import com.benjorb.Tippapp.Dto.UserTotalPoints;
import com.benjorb.Tippapp.Model.Match;
import com.benjorb.Tippapp.Model.Tipps;
import com.benjorb.Tippapp.Model.Users;
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
