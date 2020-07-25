package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(1, LocalDateTime.of(2015, Month.JANUARY, 29, 10,  0), "Завтрак", 700),
            new Meal(1, LocalDateTime.of(2015, Month.JANUARY, 29, 13,  30), "Обед", 800),
            new Meal(1, LocalDateTime.of(2015, Month.JANUARY, 29, 19,  0), "Ужин", 480),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
            new Meal(1, LocalDateTime.of(2015, Month.FEBRUARY, 1, 10,  0), "Завтрак", 1000),
            new Meal(1, LocalDateTime.of(2015, Month.FEBRUARY, 1, 13,  0), "Обед", 500),
            new Meal(1, LocalDateTime.of(2015, Month.FEBRUARY, 1, 16,  0), "Перекус", 250),
            new Meal(1, LocalDateTime.of(2015, Month.FEBRUARY, 1, 20,  0), "Ужин", 510),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 10,  0), "Завтрак (1)", 650),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13,  0), "Обед (1)", 1150),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 20,  0), "Ужин (1)", 650),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 31, 10,  0), "Завтрак (1)", 1150),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 31, 13,  0), "Обед (1)", 650),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 31, 20,  0), "Ужин (1)", 660),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 31,  9,  0), "Завтрак (2)", 1250),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 13, 30), "Обед (2)", 670),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 19,  0), "Ужин (2)", 690)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filteredByStreams(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filteredByStreams(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenInclusive(meal.getTime(), startTime, endTime));
    }

    public static List<MealTo> filteredByStreams(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<Integer> userIds() {
        return MEALS.stream()
                .map(Meal::getUserId)
                .distinct()
                .collect(Collectors.toList());
    }

}
