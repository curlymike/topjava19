package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal {
    private LocalDateTime dateTime;
    private String description;
    private int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public synchronized LocalDateTime getDateTime() {
        return dateTime;
    }

    public synchronized String getDescription() {
        return description;
    }

    public synchronized int getCalories() {
        return calories;
    }

    public synchronized LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public synchronized LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public synchronized void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public synchronized void setDescription(String description) {
        this.description = description;
    }

    public synchronized void setCalories(int calories) {
        this.calories = calories;
    }
}
