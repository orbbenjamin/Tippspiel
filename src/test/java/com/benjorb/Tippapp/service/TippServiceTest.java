package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.model.Match;
import com.benjorb.Tippapp.model.Tipps;
import com.benjorb.Tippapp.model.Users;
import com.benjorb.Tippapp.repo.TippRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TippServiceTest {

    @Mock
    TippRepo tippRepo;

    @InjectMocks
    TippService tippService;

    @Test
    void submitTestEmpty() {
        Users user = new Users();
        Match match = new Match();

        when(tippRepo.findByUserAndMatch(user, match))
                .thenReturn(Optional.empty());

        when(tippRepo.save(any(Tipps.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Tipps result = tippService.sumbittingTipp(user, match, 2, 3);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(match, result.getMatch());
        assertEquals(2, result.getPredictedHome());
        assertEquals(3, result.getPredictedAway());

        verify(tippRepo).save(any(Tipps.class));
    }
    @Test
    void submitTestExisting() {
        Users user = new Users();
        Match match = new Match();

        Tipps existing = new Tipps();
        existing.setUser(user);
        existing.setMatch(match);
        existing.setPredictedHome(0);
        existing.setPredictedAway(0);

        when(tippRepo.findByUserAndMatch(user, match))
                .thenReturn(Optional.of(existing));

        when(tippRepo.save(any(Tipps.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Tipps result = tippService.sumbittingTipp(user, match, 0, 0);

        assertSame(existing, result);
        assertEquals(0, result.getPredictedHome());
        assertEquals(0, result.getPredictedAway());

        verify(tippRepo).save(any(Tipps.class));
    }
    @Test
    void deleteTestExisting(){
        Users user = new Users();
        Match match = new Match();
        Tipps tipps = new Tipps();


        when(tippRepo.findByUserAndMatch(user, match))
                .thenReturn(Optional.of(tipps));

        tippService.delete(user, match);

        verify(tippRepo).delete(tipps);

    }
    @Test
    void deleteTestEmpty(){
        Users user = new Users();
        Match match = new Match();



        when(tippRepo.findByUserAndMatch(user, match))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->tippService.delete(user, match));

    }
    @Test
    void findByUserMatchTest(){
        Users user = new Users();
        Match match = new Match();
        Tipps tipp = new Tipps();

        when(tippRepo.findByUserAndMatch(user, match)).thenReturn(Optional.of(tipp));

        Optional<Tipps> result = tippService.findByUserAndMatch(user, match);

        assertTrue(result.isPresent());
        assertEquals(tipp, result.get());

        verify(tippRepo).findByUserAndMatch(user, match);


    }
}
