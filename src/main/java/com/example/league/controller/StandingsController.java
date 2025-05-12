package com.example.league.controller;

import com.example.league.dto.StandingEntry;
import com.example.league.dto.StandingResponseDTO;
import com.example.league.service.StandingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/standings")
public class StandingsController {

    private final StandingsService standingsService;

    @Autowired
    public StandingsController(StandingsService standingsService) {
        this.standingsService = standingsService;
    }

    @GetMapping // Обрабатывает GET запросы на /api/standings
    public ResponseEntity<StandingResponseDTO> getStandingsTable(
            @RequestParam("date") // Обязательный параметр запроса "date"
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Указываем ожидаемый формат даты
            LocalDate date) {

        List<StandingEntry> standings = standingsService.getStandings(date);
        StandingResponseDTO response = new StandingResponseDTO();
        response.setStandings(standings);

        return ResponseEntity.ok(response); // Возвращаем DTO и статус 200 OK
    }
}