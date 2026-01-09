package com.benjorb.Tippapp.dto;

import java.util.List;

public class MatchResponse {
    private List<MatchDto> matchDtos;

    public List<MatchDto> getMatches() {
        return matchDtos;
    }

    public void setMatches(List<MatchDto> matchDtos) {
        this.matchDtos = matchDtos;
    }
}
