package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static boolean isBetweenInclusive(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetween(LocalDate localDate, LocalDate from, LocalDate to) {
        return localDate.compareTo(from) >= 0 && localDate.compareTo(to) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    // In case it is unable to parse the string
    // returns null - which is what I want.
    // Note: parse calls Objects.requireNonNull first
    // so I'm covered in case of null;
    public static LocalDate parseDate(String str) {
        try {
            return LocalDate.parse(str, DATE_FORMATTER);
        } catch (DateTimeParseException | NullPointerException e) {}
        return null;
    }

    // In case it is unable to parse the string
    // returns null - which is what I want.
    // Note: parse calls Objects.requireNonNull first
    // so I'm covered in case of null;
    public static LocalTime parseTime(String str) {
        try {
        return LocalTime.parse(str, TIME_FORMATTER);
        } catch (DateTimeParseException | NullPointerException e) {}
        return null;
    }

    public static String dateString(LocalDate ldt) {
        return (ldt == null || ldt == LocalDate.MAX || ldt == LocalDate.MIN) ? "" : ldt.format(DATE_FORMATTER);
    }

    public static String timeString(LocalTime ldt) {
        return (ldt == null || ldt == LocalTime.MAX || ldt == LocalTime.MIN) ? "" : ldt.format(TIME_FORMATTER);
    }

}

