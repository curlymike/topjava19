package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = service.create(new Meal(NEW_MEAL), USER_ID);
        Assertions.assertThat(newMeal).isNotNull();
        Assertions.assertThat(newMeal.getId()).isGreaterThan(0);
        Meal mealFromDB = service.get(newMeal.getId(), USER_ID);
        assertMatch(mealFromDB, newMeal);
        Assertions.assertThat(mealFromDB).isEqualToIgnoringGivenFields(NEW_MEAL, "id");
    }

    @Test(expected = DuplicateKeyException.class)
    public void create2() {
        Meal newMeal1  = new Meal(NEW_MEAL);
        newMeal1.setDateTime(newMeal1.getDateTime().plusDays(1));
        Meal newMeal2  = new Meal(newMeal1);
        service.create(newMeal1, USER_ID);
        service.create(newMeal2, USER_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal, MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void getAnothers() {
        service.get(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNonExisting() {
        service.get(MEAL_ID -1, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnothers() {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNonExisting() {
        service.delete(MEAL_ID - 1, USER_ID);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL);
        updated.setDescription(updated.getDescription() + " updated");
        updated.setDateTime(updated.getDateTime().minusDays(1));
        updated.setCalories(updated.getCalories() + 50);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test
    public void update2() {
        Meal updated = service.get(MEAL_ID, USER_ID);
        updated.setDescription(updated.getDescription() + " updated");
        updated.setDateTime(updated.getDateTime().minusDays(1));
        updated.setCalories(updated.getCalories() + 50);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithNew() {
        Meal newMeal = new Meal(NEW_MEAL);
        service.update(newMeal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNonExisting() {
        Meal newMeal = new Meal(NEW_MEAL);
        newMeal.setId(999_999); // Id 999_999 does not exist
        service.update(newMeal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnothers() {
        Meal updated = new Meal(MEAL);
        updated.setDescription("*** " + updated.getDescription() + " (updated)");
        updated.setDateTime(updated.getDateTime().minusDays(1));
        updated.setCalories(updated.getCalories() + 50);
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> meals = service.getBetweenHalfOpen(START_DATE, END_DATE, USER_ID);
        assertMatch(meals, Arrays.asList(MEALS_DATE_RANGE));
    }

}