package com.example.league.controller;

import com.example.league.dto.MatchDTO;
import com.example.league.entity.Match;
import com.example.league.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matches") // Базовый путь для всех эндпоинтов этого контроллера
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping // Обрабатывает POST запросы на /api/matches
    @ResponseStatus(HttpStatus.CREATED) // Устанавливаем статус ответа по умолчанию
    public ResponseEntity<Match> registerMatch(@Valid @RequestBody MatchDTO matchDto) {
        // @Valid включает валидацию DTO на основе аннотаций (@NotNull, @Size и т.д.)
        // @RequestBody говорит Spring взять тело запроса и преобразовать его в MatchDTO
        Match createdMatch = matchService.registerMatch(matchDto);
        // Возвращаем созданный матч и статус 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    // Здесь можно добавить другие эндпоинты для матчей, если нужно (GET, PUT, DELETE)
}