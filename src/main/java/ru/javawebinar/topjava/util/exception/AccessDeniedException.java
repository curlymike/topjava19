package ru.javawebinar.topjava.util.exception;

import ru.javawebinar.topjava.model.Meal;

public class AccessDeniedException extends RuntimeException {
    private final Meal meal;
    public AccessDeniedException(String message) {
        this(message, null);
    }
    public AccessDeniedException(String message, Meal meal) {
        super(message);
        this.meal = meal;
    }
    public Meal getMeal() {
        return meal;
    }
}
