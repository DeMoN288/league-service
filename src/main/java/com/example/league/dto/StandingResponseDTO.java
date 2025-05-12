package com.example.league.dto;

import java.util.List;

public class StandingResponseDTO {
    // Здесь должен быть List<StandingEntry>, а не StandingResponseDTO
    private List<StandingEntry> standings;

    // Getters and Setters
    public List<StandingEntry> getStandings() { return standings; }
    public void setStandings(List<StandingEntry> standings) { this.standings = standings; }
}