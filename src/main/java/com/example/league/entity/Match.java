package com.example.league.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "matches", uniqueConstraints = { // Указываем уникальный ключ здесь тоже
    @UniqueConstraint(columnNames = {"season", "match_date", "home_team", "away_team"}, name = "uk_match_unique")
})
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String season;

    @Column(name = "match_date", nullable = false) // Явно указываем имя колонки
    private LocalDate matchDate;

    @Column(name = "home_team", length = 100, nullable = false)
    private String homeTeam;

    @Column(name = "away_team", length = 100, nullable = false)
    private String awayTeam;

    @Column(name = "home_score", nullable = false)
    private Integer homeScore; // Используем Integer, т.к. nullable=false

    @Column(name = "away_score", nullable = false)
    private Integer awayScore;

    // Конструкторы
    public Match() {
    }

    public Match(String season, LocalDate matchDate, String homeTeam, String awayTeam, Integer homeScore, Integer awayScore) {
        this.season = season;
        this.matchDate = matchDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    // Геттеры и Сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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

    // equals, hashCode, toString (важно для корректной работы с коллекциями и логами)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        // Сравниваем по уникальным полям, если id еще нет
        if (id != null && match.id != null) {
            return Objects.equals(id, match.id);
        }
        return Objects.equals(season, match.season) &&
               Objects.equals(matchDate, match.matchDate) &&
               Objects.equals(homeTeam, match.homeTeam) &&
               Objects.equals(awayTeam, match.awayTeam);
    }

    @Override
    public int hashCode() {
        // Используем те же поля, что и в equals для консистентности
        return Objects.hash(id, season, matchDate, homeTeam, awayTeam);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", season='" + season + '\'' +
                ", matchDate=" + matchDate +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                '}';
    }
}