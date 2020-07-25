package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeCheck {

    private final LocalTime timeFrom;
    private final LocalTime timeTo;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public DateTimeCheck(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo) {
        this.timeFrom = timeFrom == null ? LocalTime.MIN : timeFrom;
        this.timeTo = timeTo == null ? LocalTime.MAX : timeTo;
        this.dateFrom = dateFrom == null ? LocalDate.MIN : dateFrom;
        this.dateTo = dateTo == null ? LocalDate.MAX : dateTo;
    }

    public boolean isBetween(Meal meal) {
        return meal.getDate().compareTo(dateFrom) >= 0
                && meal.getDate().compareTo(dateTo) <= 0
                && meal.getTime().compareTo(timeFrom) >= 0
                && meal.getTime().compareTo(timeTo) <= 0;
    }

    public <T> T valueByName(String name) {
        switch (name) {
            case "dateFrom": return (T) dateFrom;
            case "dateTo": return (T) dateTo;
            case "timeFrom": return (T) timeFrom;
            case "timeTo": return (T) timeTo;
        }
        return null;
    }

    public LocalTime timeFrom() {
        return timeFrom;
    }

    public LocalTime timeTo() {
        return timeTo;
    }

    public LocalDate dateFrom() {
        return dateFrom;
    }

    public LocalDate dateTo() {
        return dateTo;
    }
}
