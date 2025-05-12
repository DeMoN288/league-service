package com.example.league.service;

import com.example.league.dto.MatchDTO;
import com.example.league.entity.Match;
import com.example.league.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional // Гарантирует, что операция будет выполнена в транзакции
    public Match registerMatch(MatchDTO matchDto) {
        // Преобразование DTO в Entity
        Match match = new Match();
        match.setSeason(matchDto.getSeason());
        match.setMatchDate(matchDto.getMatchDate());
        match.setHomeTeam(matchDto.getHomeTeam());
        match.setAwayTeam(matchDto.getAwayTeam());
        match.setHomeScore(matchDto.getHomeScore());
        match.setAwayScore(matchDto.getAwayScore());

        // Сохранение в БД
        // Обработка DataIntegrityViolationException (дубликат) будет в GlobalExceptionHandler
        return matchRepository.save(match);
    }
}