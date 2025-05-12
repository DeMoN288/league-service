package com.example.league.service;

import com.example.league.dto.StandingEntry;
import com.example.league.entity.Match;
import com.example.league.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StandingsService {

    private final MatchRepository matchRepository;

    // Константы для очков
    private static final int POINTS_FOR_WIN = 3;
    private static final int POINTS_FOR_DRAW = 1;
    private static final int POINTS_FOR_LOSS = 0;


    @Autowired
    public StandingsService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true) // Транзакция только для чтения, т.к. мы не меняем данные
    public List<StandingEntry> getStandings(LocalDate date) {
        // 1. Получаем все матчи, сыгранные до указанной даты (включительно)
        List<Match> matches = matchRepository.findByMatchDateLessThanEqualOrderByMatchDateAsc(date);

        // 2. Используем Map для хранения и обновления статистики команд
        Map<String, StandingEntry> standingsMap = new HashMap<>();

        // 3. Обрабатываем каждый матч
        for (Match match : matches) {
            // Получаем или создаем записи для обеих команд
            StandingEntry homeEntry = standingsMap.computeIfAbsent(match.getHomeTeam(), teamName -> new StandingEntry(teamName, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            StandingEntry awayEntry = standingsMap.computeIfAbsent(match.getAwayTeam(), teamName -> new StandingEntry(teamName, 0, 0, 0, 0, 0, 0, 0, 0, 0));

            // Обновляем общие показатели
            homeEntry.setPlayedGames(homeEntry.getPlayedGames() + 1);
            awayEntry.setPlayedGames(awayEntry.getPlayedGames() + 1);

            homeEntry.setGoalsFor(homeEntry.getGoalsFor() + match.getHomeScore());
            homeEntry.setGoalsAgainst(homeEntry.getGoalsAgainst() + match.getAwayScore());
            homeEntry.setGoalDifference(homeEntry.getGoalsFor() - homeEntry.getGoalsAgainst());

            awayEntry.setGoalsFor(awayEntry.getGoalsFor() + match.getAwayScore());
            awayEntry.setGoalsAgainst(awayEntry.getGoalsAgainst() + match.getHomeScore());
            awayEntry.setGoalDifference(awayEntry.getGoalsFor() - awayEntry.getGoalsAgainst());

            // Обновляем очки, победы, ничьи, поражения
            if (match.getHomeScore() > match.getAwayScore()) { // Победа хозяев
                homeEntry.setWins(homeEntry.getWins() + 1);
                homeEntry.setPoints(homeEntry.getPoints() + POINTS_FOR_WIN);
                awayEntry.setLosses(awayEntry.getLosses() + 1);
                awayEntry.setPoints(awayEntry.getPoints() + POINTS_FOR_LOSS);
            } else if (match.getHomeScore() < match.getAwayScore()) { // Победа гостей
                homeEntry.setLosses(homeEntry.getLosses() + 1);
                homeEntry.setPoints(homeEntry.getPoints() + POINTS_FOR_LOSS);
                awayEntry.setWins(awayEntry.getWins() + 1);
                awayEntry.setPoints(awayEntry.getPoints() + POINTS_FOR_WIN);
            } else { // Ничья
                homeEntry.setDraws(homeEntry.getDraws() + 1);
                homeEntry.setPoints(homeEntry.getPoints() + POINTS_FOR_DRAW);
                awayEntry.setDraws(awayEntry.getDraws() + 1);
                awayEntry.setPoints(awayEntry.getPoints() + POINTS_FOR_DRAW);
            }
        }

        // 4. Преобразуем Map в List
        List<StandingEntry> standingsList = new ArrayList<>(standingsMap.values());

        // 5. Сортируем список по правилам:
        //    - Очки (по убыванию)
        //    - Разница мячей (по убыванию)
        //    - Забитые мячи (по убыванию)
        //    - Название команды (по возрастанию - для стабильности)
        standingsList.sort(
                Comparator.comparingInt(StandingEntry::getPoints).reversed()
                .thenComparing(Comparator.comparingInt(StandingEntry::getGoalDifference).reversed())
                .thenComparing(Comparator.comparingInt(StandingEntry::getGoalsFor).reversed())
                .thenComparing(StandingEntry::getTeamName)
        );

        // 6. Присваиваем позиции (места)
        int position = 1;
        for (StandingEntry entry : standingsList) {
            entry.setPosition(position++);
        }

        return standingsList;
    }
}