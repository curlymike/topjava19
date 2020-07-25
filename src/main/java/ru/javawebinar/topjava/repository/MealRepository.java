package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal);

    // false if not found
    boolean delete(int id);

    // null if not found
    Meal get(int id);

    // null if not found
    Meal get(int id, int userId);

    Collection<Meal> getAll();

    Collection<Meal> getAll(Integer userId);

    List<Meal> getAll(Integer userId, LocalDate dateFrom, LocalDate dateTo);
}
