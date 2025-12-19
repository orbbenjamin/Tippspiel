package com.benjorb.Tippapp.Controller;

import com.benjorb.Tippapp.Model.Match;
import com.benjorb.Tippapp.Service.MatchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MatchControllerTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchContoller matchContoller;

    @Test
    void getOneGameSuccess(){
        Match match = new Match(1);
        Mockito.when(matchService.findById(1)).thenReturn(match);

        ResponseEntity<Match> response = matchContoller.getOneGame(1);
        assertEquals(HttpStatus.OK ,response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());

        Mockito.verify(matchService).findById(1);

    }
    @Test
    void getOneGameFail(){
        Match match = new Match();
        Mockito.when(matchService.findById(1)).thenReturn(null);

        ResponseEntity<Match> response = matchContoller.getOneGame(1);
        assertEquals(HttpStatus.NOT_FOUND ,response.getStatusCode());

        Mockito.verify(matchService).findById(1);

    }
    @Test
    void getAllGamesSuccess(){
       List<Match> newMatches = List.of(new Match(1), new Match(2));
       Mockito.when(matchService.getAllGames()).thenReturn(newMatches);

       ResponseEntity<List<Match>> response = matchContoller.getAllGames();

       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertNotNull(response.getBody());
       assertEquals(2, response.getBody().size());

       Mockito.verify(matchService).getAllGames();

    }
    @Test
    void getAllGamesFail(){

        Mockito.when(matchService.getAllGames()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Match>> response = matchContoller.getAllGames();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());


        Mockito.verify(matchService).getAllGames();

    }


}
