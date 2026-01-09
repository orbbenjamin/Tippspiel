package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.model.Match;
import com.benjorb.Tippapp.model.Tipps;
import com.benjorb.Tippapp.model.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScoringServiceTest {
    @Mock
    private TippService tippService;
    @Mock
    private MatchService matchService;
    @Mock
    private UserService userService;

    @InjectMocks
    private ScoringService scoringService;

    @Test
    void testCalculateAndSavePoints_withExistingTipp() {
        int matchId = 540;
        int userId = 2;

        Match match = new Match();
        Users user = new Users();
        Tipps tipps = new Tipps();

        when(matchService.findById(matchId)).thenReturn(match);
        when(userService.findByIdWithoutCheck(userId)).thenReturn(user);
        when(tippService.findByUserAndMatch(user, match)).thenReturn(Optional.of(tipps));


        ScoringService scoringServiceSpy = spy(scoringService);


        doReturn(2.92).when(scoringServiceSpy).calculatePoints(match, tipps);

        double result = scoringServiceSpy.calculateAndSavePoints(matchId, userId);

        assertEquals(2.92, result);
        assertEquals(2.92, tipps.getPoints());
        verify(tippService).save(tipps);
    }

    @Test
    void noZero() {
        int matchId = 540517;
        int userId = 22;

        Match match = new Match();
        Users user = new Users();

        when(matchService.findById(matchId)).thenReturn(match);
        when(userService.findByIdWithoutCheck(userId)).thenReturn(user);
        when(tippService.findByUserAndMatch(user, match)).thenReturn(Optional.empty());

        double result = scoringService.calculateAndSavePoints(matchId, userId);

        assertEquals(0.0, result);
        verify(tippService, never()).save(any());
    }
}

