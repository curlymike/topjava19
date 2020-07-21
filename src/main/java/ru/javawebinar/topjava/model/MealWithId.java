package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealWithId extends Meal {
    private final Integer id;

    public MealWithId(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(dateTime, description, calories);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
