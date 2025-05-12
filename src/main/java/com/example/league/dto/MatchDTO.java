package com.example.league.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

// DTO для создания нового матча через API
public class MatchDTO {

    @Size(max = 50, message = "Season name cannot exceed 50 characters")
    private String season; // Сезон может быть не обязательным

    @NotNull(message = "Match date is required")
    private LocalDate matchDate;

    @NotBlank(message = "Home team name is required")
    @Size(max = 100, message = "Home team name cannot exceed 100 characters")
    private String homeTeam;

    @NotBlank(message = "Away team name is required")
    @Size(max = 100, message = "Away team name cannot exceed 100 characters")
    private String awayTeam;

    @NotNull(message = "Home score is required")
    @Min(value = 0, message = "Score cannot be negative")
    private Integer homeScore;

    @NotNull(message = "Away score is required")
    @Min(value = 0, message = "Score cannot be negative")
    private Integer awayScore;

    // Геттеры и Сеттеры (необходимы для Jackson и валидации)
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }
    public String getHomeTeam() { return homeTeam; }
    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }
    public String getAwayTeam() { return awayTeam; }
    public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }
    public Integer getHomeScore() { return homeScore; }
    public void setHomeScore(Integer homeScore) { this.homeScore = homeScore; }
    public Integer getAwayScore() { return awayScore; }
    public void setAwayScore(Integer awayScore) { this.awayScore = awayScore; }
}