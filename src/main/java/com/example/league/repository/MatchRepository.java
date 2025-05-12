package com.example.league.repository;

import com.example.league.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    // Метод для получения всех матчей, сыгранных до указанной даты (включительно)
    // Spring Data JPA автоматически сгенерирует SQL запрос по имени метода
    List<Match> findByMatchDateLessThanEqualOrderByMatchDateAsc(LocalDate date);

    // Можно добавить и другие методы, если понадобятся, например:
    // List<Match> findBySeasonAndMatchDateLessThanEqual(String season, LocalDate date);
}