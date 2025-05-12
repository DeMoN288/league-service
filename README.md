# League Service

Сервис для управления результатами матчей и отображения турнирной таблицы чемпионата.

## Технологии

*   Java 17
*   Spring Boot 3.1.5
*   Spring Data JPA
*   PostgreSQL
*   Liquibase
*   Maven

## Настройка

1.  **База данных:**
    *   Установите PostgreSQL (если еще не установлен).
    *   Создайте базу данных, например, `league_db`.
    *   Создайте пользователя (или используйте существующего) с правами на эту БД.

2.  **Конфигурация:**
    *   Откройте файл `src/main/resources/application.properties`.
    *   Укажите правильные параметры для подключения к вашей базе данных:
        *   `spring.datasource.url=jdbc:postgresql://localhost:5432/league_db` (замените `localhost:5432` и `league_db` при необходимости)
        *   `spring.datasource.username=your_db_user` (замените `your_db_user`)
        *   `spring.datasource.password=your_db_password` (замените `your_db_password`)
    *   Liquibase настроен на автоматическое применение миграций при старте приложения.

## Сборка и Запуск

1.  **Сборка:**
    ```bash
    mvn clean install
    ```
    Это создаст исполняемый JAR-файл в папке `target/`.

2.  **Запуск:**
    *   Через Maven:
        ```bash
        mvn spring-boot:run
        ```
    *   Запуск JAR-файла:
        ```bash
        java -jar target/league-service-1.0.0.jar
        ```

Приложение будет доступно по адресу `http://localhost:8080` (если порт не изменен).

## API Endpoints

*   **`POST /api/matches`**: Регистрация нового результата матча.
    *   **Тело запроса (JSON):**
        ```json
        {
          "season": "2024/2025",
          "matchDate": "2025-05-15",
          "homeTeam": "Team A",
          "awayTeam": "Team B",
          "homeScore": 2,
          "awayScore": 1
        }
        ```
    *   **Ответ:** `201 Created` с данными созданного матча.

*   **`GET /api/standings?date={YYYY-MM-DD}`**: Получение турнирной таблицы на указанную дату.
    *   **Параметр запроса:** `date` (обязательный) - дата в формате `YYYY-MM-DD`.
    *   **Пример:** `GET /api/standings?date=2025-05-16`
    *   **Ответ:** `200 OK` с JSON объектом, содержащим список `standings`:
        ```json
        {
          "standings": [
            {
              "teamName": "Team A",
              "position": 1,
              "points": 3,
              "playedGames": 1,
              "wins": 1,
              "draws": 0,
              "losses": 0,
              "goalsFor": 2,
              "goalsAgainst": 1,
              "goalDifference": 1
            },
            {
              "teamName": "Team B",
              "position": 2,
              "points": 0,
              "playedGames": 1,
              "wins": 0,
              "draws": 0,
              "losses": 1,
              "goalsFor": 1,
              "goalsAgainst": 2,
              "goalDifference": -1
            }
            // ... другие команды
          ]
        }
        ```