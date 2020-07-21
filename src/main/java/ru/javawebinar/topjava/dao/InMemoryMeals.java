package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class InMemoryMeals {
    private static final AtomicInteger nextId = new AtomicInteger(1);
    private static final InMemoryMeals instance = new InMemoryMeals();

    private final Map<Integer, MealWithId> meals = new HashMap<>();

    private InMemoryMeals() {
        init();
    }

    private void init() {
        for (Meal meal : HardcodedMeals.getInstance().getAll()) {
            addMeal(meal);
        }
    }

    public static synchronized int getId() {
        return nextId.getAndIncrement();
    }

    public static InMemoryMeals getInstance() {
        return instance;
    }

    public MealWithId newMeal() {
        return new MealWithId(null, LocalDateTime.now(), "", 0);
    }

    public MealWithId addMeal(Meal meal) {
        MealWithId mealWithId = new MealWithId(getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        return meals.put(mealWithId.getId(), mealWithId);
    }

    public MealWithId save(MealWithId meal) {
        if (meal.getId() == null) {
            meal = new MealWithId(getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
            return meals.put(meal.getId(), meal);
        }
        return meal;
    }

    public boolean exists(Integer id) {
        return meals.get(id) != null;
    }

    public boolean notExists(Integer id) {
        return meals.get(id) == null;
    }

    public MealWithId get(Integer id) {
        return meals.get(id);
    }

    public Map<Integer, MealWithId> getAll() {
        return meals;
    }

    public List<WithIdWithExceed> getAllWithExceed(int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumByDate = meals.values().stream()
            .collect(
                Collectors.groupingBy(MealWithId::getDate, Collectors.summingInt(MealWithId::getCalories))
            );

        return meals.values().stream()
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    private WithIdWithExceed createWithExceed(MealWithId meal, boolean exceed) {
        return new MealWithIdWithExceed(meal, exceed);
    }

}
