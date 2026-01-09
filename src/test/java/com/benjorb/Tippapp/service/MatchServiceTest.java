package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.dto.*;
import com.benjorb.Tippapp.model.Gameday;
import com.benjorb.Tippapp.model.Match;
import com.benjorb.Tippapp.repo.GamedayRepo;
import com.benjorb.Tippapp.repo.MatchRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @Mock
    private MatchRepo matchRepo;
    @Mock
    private FootballApiService footballApiService;
    @Mock
    private GamedayRepo gamedayRepo;
    @InjectMocks
    private MatchService matchService;

    static Stream<MatchResponse> invalidApi(){
        MatchResponse nullMatch = new MatchResponse();
        nullMatch.setMatches(null);

        MatchResponse emptyMatch = new MatchResponse();
        emptyMatch.setMatches(List.of());

        return Stream.of(null, nullMatch, emptyMatch);
    }

    @ParameterizedTest
    @MethodSource("invalidApi")
    void getCurrentMatchdayApiTestWhenFail(MatchResponse response){

        when(footballApiService.getMatches(0)).thenReturn(response);
        int result = matchService.getCurrentMatchdayFromApi();

        assertEquals(-1, result);

    }
    @Test
    void getCurrentMatchdayApiSuccess(){
        Season season = new Season();
        season.setCurrentMatchday(5);

        MatchDto matchDto = new MatchDto();
        matchDto.setSeason(season);

        MatchResponse matchResponse = new MatchResponse();
        matchResponse.setMatches(List.of(matchDto));

        when(footballApiService.getMatches(0)).thenReturn(matchResponse);

        int result = matchService.getCurrentMatchdayFromApi();
        assertEquals(5, result);


    }
    @Test
    void canPlaceTipTest(){


        MatchDto live = new MatchDto();
        live.setStatus("Live");
        MatchDto finished = new MatchDto();
        finished.setStatus("FINISHED");
        MatchDto valid = new MatchDto();
        valid.setStatus("SCHEDULED");

        assertFalse(matchService.canPlaceTip(live));
        assertFalse(matchService.canPlaceTip(finished));
        assertTrue(matchService.canPlaceTip(valid));
    }
    private MatchResponse mockResponseWithCurrentMatchday(int matchday) {
        Season season = new Season();
        season.setCurrentMatchday(matchday);

        MatchDto dto = new MatchDto();
        dto.setSeason(season);

        MatchResponse response = new MatchResponse();
        response.setMatches(List.of(dto));

        return response;
    }
    @Test
    void updateMatchday_doesNothing_whenCurrentMatchdayIsMinusOne() {
        when(footballApiService.getMatches(0)).thenReturn(null);

        matchService.updateMatchday();

        verifyNoInteractions(matchRepo);
        verifyNoInteractions(gamedayRepo);
    }
    @Test
    void updateMatchday_doesNothing_whenNoMatchesReturned() {
        MatchResponse response = new MatchResponse();
        response.setMatches(List.of());

        when(footballApiService.getMatches(0)).thenReturn(mockResponseWithCurrentMatchday(5));
        when(footballApiService.getMatches(5)).thenReturn(response);

        matchService.updateMatchday();

        Mockito.verify(matchRepo, never()).save(any());
        Mockito.verify(gamedayRepo, never()).save(any());
    }
    private MatchResponse mockMatchResponse() {
        MatchDto dto = new MatchDto();
        dto.setId(1);
        dto.setMatchday(5);
        dto.setStatus("SCHEDULED");

        Team home = new Team();
        home.setName("Bayern");

        Team away = new Team();
        away.setName("Dortmund");

        Score score = new Score();
        FullTime ft = new FullTime();
        ft.setHome(2);
        ft.setAway(1);
        score.setFullTime(ft);

        dto.setHomeTeam(home);
        dto.setAwayTeam(away);
        dto.setScore(score);

        MatchResponse response = new MatchResponse();
        response.setMatches(List.of(dto));

        return response;
    }

    @Test
    void updateMatchday_savesMatchesAndUpdatesGameday() {

        // --- given ---
        int currentMatchday = 5;

        when(footballApiService.getMatches(0)).thenReturn(mockResponseWithCurrentMatchday(currentMatchday));
        when(footballApiService.getMatches(currentMatchday)).thenReturn(mockMatchResponse());

        when(matchRepo.findById(1)).thenReturn(java.util.Optional.empty());
        when(gamedayRepo.findById(1)).thenReturn(Optional.of(new Gameday()));

        // --- when ---
        matchService.updateMatchday();

        // --- then ---
        verify(matchRepo, times(1)).save(any(Match.class));

        ArgumentCaptor<Gameday> captor = ArgumentCaptor.forClass(Gameday.class);
        verify(gamedayRepo).save(captor.capture());

        assertEquals(currentMatchday, captor.getValue().getCurrentMatchday());
    }
    @Test
    void findAllWithCurrentMatchday_returnsMatchesForCurrentMatchday() {

        // given
        Gameday gameday = new Gameday();
        gameday.setCurrentMatchday(7);

        List<Match> matches = List.of(new Match(), new Match());

        when(gamedayRepo.findById(1)).thenReturn(Optional.of(gameday));
        when(matchRepo.findByMatchDay(7)).thenReturn(matches);

        // when
        List<Match> result = matchService.findAllWithCurrentMatchday();

        // then
        assertEquals(2, result.size());
        verify(matchRepo).findByMatchDay(7);
    }



}
