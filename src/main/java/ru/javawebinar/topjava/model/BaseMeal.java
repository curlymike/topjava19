package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface BaseMeal {
    LocalDateTime getDateTime();
    String getDescription();
    int getCalories();
    LocalDate getDate();
    LocalTime getTime();
}
