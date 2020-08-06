package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class MealTestData {
    public static final int MEAL_ID = 90000;
    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = 100001;
    public static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 10, 0, 0);
    public static final LocalDateTime DATE_TIME_NEW = LocalDateTime.of(2015, Month.JUNE, 1, 10, 0, 0);
    public static final String DESCRIPTION = "Завтрак";
    public static final int CALORIES = 500;
    public static final Meal MEAL = new Meal(MEAL_ID, DATE_TIME, DESCRIPTION, CALORIES);
    public static final Meal NEW_MEAL = new Meal(DATE_TIME_NEW, DESCRIPTION, CALORIES);

    public static final LocalDate START_DATE = LocalDate.of(2015, Month.MAY, 31);
    public static final LocalDate END_DATE = LocalDate.of(2015, Month.JUNE, 1);

    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 20, 0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2015, Month.JUNE, 1, 18, 0);

    public static final Meal[] MEALS_DATE_RANGE = {
            new Meal(90008, LocalDateTime.of(2015, Month.JUNE, 1, 18, 0, 0), "Ужин", 480),
            new Meal(90007, LocalDateTime.of(2015, Month.JUNE, 1, 13, 0, 0), "Обед", 610),
            new Meal(90006, LocalDateTime.of(2015, Month.JUNE, 1, 9, 0, 0), "Завтрак", 900),
            new Meal(90005, LocalDateTime.of(2015, Month.MAY, 31, 20, 0, 0), "Ужин", 510),
            new Meal(90004, LocalDateTime.of(2015, Month.MAY, 31, 13, 0, 0), "Обед", 500),
            new Meal(90003, LocalDateTime.of(2015, Month.MAY, 31, 10, 0, 0), "Завтрак", 1000),
    };

    public static final Meal[] MEALS_DATE_TIME_RANGE = {
            new Meal(90008, LocalDateTime.of(2015, Month.JUNE, 1, 18, 0, 0), "Ужин", 480),
            new Meal(90007, LocalDateTime.of(2015, Month.JUNE, 1, 13, 0, 0), "Обед", 610),
            new Meal(90006, LocalDateTime.of(2015, Month.JUNE, 1, 9, 0, 0), "Завтрак", 900),
            new Meal(90005, LocalDateTime.of(2015, Month.MAY, 31, 20, 0, 0), "Ужин", 510),
            new Meal(90004, LocalDateTime.of(2015, Month.MAY, 31, 13, 0, 0), "Обед", 500),
            new Meal(90003, LocalDateTime.of(2015, Month.MAY, 31, 10, 0, 0), "Завтрак", 1000),
            new Meal(90002, LocalDateTime.of(2015, Month.MAY, 30, 20, 0, 0), "Ужин", 500),
    };


    public static void assertMatch(Meal actual, Meal expected) {
        Assertions.assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
