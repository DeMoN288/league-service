package com.example.league.service;

import com.example.league.dto.StandingEntry;
import com.example.league.entity.Match;
import com.example.league.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Используем Mockito с JUnit 5
class StandingsServiceTest {

    @Mock // Создаем мок для репозитория
    private MatchRepository matchRepository;

    @InjectMocks // Внедряем моки в тестируемый сервис
    private StandingsService standingsService;

    private LocalDate testDate;
    private Match match1;
    private Match match2;
    private Match match3;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2025, 5, 20);
        // Создаем тестовые матчи
        match1 = new Match("S1", testDate.minusDays(2), "Team A", "Team B", 2, 1); // A wins
        match2 = new Match("S1", testDate.minusDays(1), "Team C", "Team A", 0, 0); // Draw
        match3 = new Match("S1", testDate, "Team B", "Team C", 3, 1);             // B wins
    }

    @Test
    void getStandings_ShouldCalculateCorrectly() {
        // Настраиваем мок: когда будет вызван findByMatchDateLessThanEqual, вернуть наш список матчей
        when(matchRepository.findByMatchDateLessThanEqualOrderByMatchDateAsc(testDate))
                .thenReturn(Arrays.asList(match1, match2, match3));

        // Вызываем тестируемый метод
        List<StandingEntry> standings = standingsService.getStandings(testDate);

        // Проверяем результат
        assertNotNull(standings);
        assertEquals(3, standings.size()); // Должно быть 3 команды

        // Проверяем порядок и данные (A=4pts, B=3pts, C=1pt)
        // 1. Team A
        StandingEntry teamA = standings.get(0);
        assertEquals("Team A", teamA.getTeamName());
        assertEquals(1, teamA.getPosition());
        assertEquals(4, teamA.getPoints());
        assertEquals(2, teamA.getPlayedGames());
        assertEquals(1, teamA.getWins());
        assertEquals(1, teamA.getDraws());
        assertEquals(0, teamA.getLosses());
        assertEquals(2, teamA.getGoalsFor());
        assertEquals(1, teamA.getGoalsAgainst());
        assertEquals(1, teamA.getGoalDifference());

        // 2. Team B
        StandingEntry teamB = standings.get(1);
        assertEquals("Team B", teamB.getTeamName());
        assertEquals(2, teamB.getPosition());
        assertEquals(3, teamB.getPoints());
        assertEquals(2, teamB.getPlayedGames());
        assertEquals(1, teamB.getWins());
        assertEquals(0, teamB.getDraws());
        assertEquals(1, teamB.getLosses());
        assertEquals(4, teamB.getGoalsFor());
        assertEquals(3, teamB.getGoalsAgainst());
        assertEquals(1, teamB.getGoalDifference()); // У A и B одинаковая разница, A выше по имени

        // 3. Team C
        StandingEntry teamC = standings.get(2);
        assertEquals("Team C", teamC.getTeamName());
        assertEquals(3, teamC.getPosition());
        assertEquals(1, teamC.getPoints());
        assertEquals(2, teamC.getPlayedGames());
        assertEquals(0, teamC.getWins());
        assertEquals(1, teamC.getDraws());
        assertEquals(1, teamC.getLosses());
        assertEquals(1, teamC.getGoalsFor());
        assertEquals(3, teamC.getGoalsAgainst());
        assertEquals(-2, teamC.getGoalDifference());

        // Убедимся, что метод репозитория был вызван ровно 1 раз с правильной датой
        verify(matchRepository, times(1)).findByMatchDateLessThanEqualOrderByMatchDateAsc(testDate);
    }

    @Test
    void getStandings_WhenNoMatches_ShouldReturnEmptyList() {
        // Настраиваем мок: вернуть пустой список
        when(matchRepository.findByMatchDateLessThanEqualOrderByMatchDateAsc(testDate))
                .thenReturn(Collections.emptyList());

        List<StandingEntry> standings = standingsService.getStandings(testDate);

        assertNotNull(standings);
        assertTrue(standings.isEmpty());
        verify(matchRepository, times(1)).findByMatchDateLessThanEqualOrderByMatchDateAsc(testDate);
    }
}