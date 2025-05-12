package com.example.league.dto;

// Класс для представления одной записи (строки) в турнирной таблице
public class StandingEntry {

    private String teamName;    // Название команды
    private int position;       // Место (позиция)
    private int points;         // Очки
    private int playedGames;    // Сыграно игр
    private int wins;           // Победы
    private int draws;          // Ничьи
    private int losses;         // Поражения
    private int goalsFor;       // Забито голов
    private int goalsAgainst;   // Пропущено голов
    private int goalDifference; // Разница мячей

    // Конструктор по умолчанию
    public StandingEntry() {
    }

    // Конструктор со всеми полями
    public StandingEntry(String teamName, int position, int points, int playedGames, int wins, int draws, int losses, int goalsFor, int goalsAgainst, int goalDifference) {
        this.teamName = teamName;
        this.position = position;
        this.points = points;
        this.playedGames = playedGames;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
    }

    // --- Геттеры и Сеттеры ---
    // (Скопируйте их из моего предыдущего ответа, если удалили)
     public String getTeamName() { return teamName; }
     public void setTeamName(String teamName) { this.teamName = teamName; }
     public int getPosition() { return position; }
     public void setPosition(int position) { this.position = position; }
     public int getPoints() { return points; }
     public void setPoints(int points) { this.points = points; }
     public int getPlayedGames() { return playedGames; }
     public void setPlayedGames(int playedGames) { this.playedGames = playedGames; }
     public int getWins() { return wins; }
     public void setWins(int wins) { this.wins = wins; }
     public int getDraws() { return draws; }
     public void setDraws(int draws) { this.draws = draws; }
     public int getLosses() { return losses; }
     public void setLosses(int losses) { this.losses = losses; }
     public int getGoalsFor() { return goalsFor; }
     public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }
     public int getGoalsAgainst() { return goalsAgainst; }
     public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }
     public int getGoalDifference() { return goalDifference; }
     public void setGoalDifference(int goalDifference) { this.goalDifference = goalDifference; }

    @Override
    public String toString() {
        // ... (toString метод)
         return "StandingEntry{" + "teamName='" + teamName + '\'' + ", position=" + position + ", points=" + points + ", playedGames=" + playedGames + ", wins=" + wins + ", draws=" + draws + ", losses=" + losses + ", goalsFor=" + goalsFor + ", goalsAgainst=" + goalsAgainst + ", goalDifference=" + goalDifference + '}';
    }
}