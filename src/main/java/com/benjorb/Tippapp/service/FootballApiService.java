package com.benjorb.Tippapp.service;

import com.benjorb.Tippapp.dto.MatchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class FootballApiService {

    @Value("${football.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public MatchResponse getMatches(Integer matchday) {
        String url = "https://api.football-data.org/v4/competitions/BL1/matches";
        if (matchday != null && matchday >= 1 && matchday <= 46) {
            url += "?matchday=" + matchday;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            System.out.println("Sende API Request an URL: " + url);
            ResponseEntity<MatchResponse> response = restTemplate.exchange(url, HttpMethod.GET, request, MatchResponse.class);
            System.out.println("API Antwort Status: " + response.getStatusCode());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.err.println("API Request Fehler: " + e.getStatusCode());
            System.err.println("Fehler Antwort: " + e.getResponseBodyAsString());
            throw e;
        }
    }

}











