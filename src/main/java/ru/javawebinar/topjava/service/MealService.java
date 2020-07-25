package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Meal get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public void update(Meal meal) throws NotFoundException {
        Meal existingMeal = get(meal.getId());
        // No need to check existingMeal for null, get() throws NotFoundException
        if (!existingMeal.getUserId().equals(meal.getUserId())) {
            log.info("Attempt to update someone else's meal {} {}", existingMeal, meal);
            throw new NotFoundException("Attempt to update someone else's meal");
        }
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }

    public Collection<Meal> getAll(Integer userId, LocalDate from, LocalDate to) {
        return repository.getAll(userId, from, to);
    }

    public Collection<Meal> getAll(Integer userId) {
        return repository.getAll(userId);
    }

    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    public int size() {
        return repository.getAll().size();
    }
}