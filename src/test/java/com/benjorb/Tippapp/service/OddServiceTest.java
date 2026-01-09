package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.model.Match;
import com.benjorb.Tippapp.model.Odds;
import com.benjorb.Tippapp.repo.OddRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OddServiceTest {

    @Mock
    private OddRepo repo;

    @Mock
    private MatchService matchService;

    @InjectMocks
    private OddService oddService;

    @Test
    void submittingUpdatingTest(){

        Match match = new Match();
        Odds existing = new Odds();

        when(matchService.findById(1)).thenReturn(match);
        when(repo.findByMatch(match)).thenReturn(Optional.of(existing));

        oddService.submittingOdds(1.5, 3.2, 2.8, 1);
        assertEquals(1.5, existing.getHomeWin());
        assertEquals(3.2, existing.getDraw());
        assertEquals(2.8, existing.getAwayWin());
        verify(repo).save(existing);


    }
    @Test
    void submittingNotExistentTest(){

        Match match = new Match();

        when(matchService.findById(1)).thenReturn(match);
        when(repo.findByMatch(match)).thenReturn(Optional.empty());

        oddService.submittingOdds(1.5, 3.2, 2.8, 1);
        verify(repo).save(any(Odds.class));


    }

}