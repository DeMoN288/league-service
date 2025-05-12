package com.example.league.controller;

import com.example.league.dto.StandingEntry;
import com.example.league.service.StandingsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StandingsController.class) // Тестируем только слой контроллера
class StandingsControllerTest {

    @Autowired
    private MockMvc mockMvc; // Позволяет выполнять HTTP запросы к контроллеру

    @MockBean // Создаем мок для сервиса, т.к. нам не нужна реальная логика БД
    private StandingsService standingsService;

    @Test
    void getStandingsTable_ShouldReturnStandings() throws Exception {
        LocalDate testDate = LocalDate.of(2025, 5, 16);
        String testDateString = "2025-05-16";

        // Создаем тестовые данные, которые должен вернуть мок-сервис
        StandingEntry entry1 = new StandingEntry("Team X", 1, 3, 1, 1, 0, 0, 2, 0, 2);
        StandingEntry entry2 = new StandingEntry("Team Y", 2, 0, 1, 0, 0, 1, 0, 2, -2);
        List<StandingEntry> mockStandings = Arrays.asList(entry1, entry2);

        // Настраиваем мок-сервис
        when(standingsService.getStandings(testDate)).thenReturn(mockStandings);

        // Выполняем GET запрос и проверяем результат
        mockMvc.perform(get("/api/standings")
                        .param("date", testDateString) // Добавляем параметр запроса
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Ожидаем JSON
                .andExpect(jsonPath("$.standings", hasSize(2))) // Ожидаем массив standings из 2 элементов
                .andExpect(jsonPath("$.standings[0].teamName", is("Team X"))) // Проверяем поля первого элемента
                .andExpect(jsonPath("$.standings[0].position", is(1)))
                .andExpect(jsonPath("$.standings[0].points", is(3)))
                .andExpect(jsonPath("$.standings[1].teamName", is("Team Y"))) // Проверяем поля второго элемента
                .andExpect(jsonPath("$.standings[1].position", is(2)))
                .andExpect(jsonPath("$.standings[1].points", is(0)));
    }

    @Test
    void getStandingsTable_WhenNoDateParam_ShouldReturnBadRequest() throws Exception {
        // Выполняем GET запрос без параметра date
        mockMvc.perform(get("/api/standings")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Ожидаем статус 400 Bad Request
    }

     @Test
    void getStandingsTable_WhenInvalidDateFormat_ShouldReturnBadRequest() throws Exception {
        // Выполняем GET запрос с неправильным форматом даты
        mockMvc.perform(get("/api/standings")
                        .param("date", "16-05-2025") // Неправильный формат
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Ожидаем статус 400 Bad Request
    }

     @Test
    void getStandingsTable_WhenServiceReturnsEmpty_ShouldReturnEmptyList() throws Exception {
        LocalDate testDate = LocalDate.of(2025, 5, 16);
        String testDateString = "2025-05-16";

        // Настраиваем мок-сервис на возврат пустого списка
        when(standingsService.getStandings(testDate)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/standings")
                        .param("date", testDateString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.standings", hasSize(0))); // Ожидаем пустой массив standings
    }
}