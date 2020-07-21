package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealWithIdWithExceed implements WithIdWithExceed {
    private MealWithId meal;
    private boolean exceed;

    public MealWithIdWithExceed(MealWithId meal, boolean exceed) {
        this.meal = meal;
        this.exceed = exceed;
    }

    @Override
    public Integer getId() {
        return meal.getId();
    }

    @Override
    public LocalDateTime getDateTime() {
        return meal.getDateTime();
    }

    @Override
    public String getDescription() {
        return meal.getDescription();
    }

    @Override
    public int getCalories() {
        return meal.getCalories();
    }

    @Override
    public LocalDate getDate() {
        return meal.getDate();
    }

    @Override
    public LocalTime getTime() {
        return meal.getTime();
    }

    public boolean isExceed() {
        return exceed;
    }
}
