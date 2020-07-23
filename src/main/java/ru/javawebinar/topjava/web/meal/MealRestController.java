package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.AccessDeniedException;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureUserIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController {
    @Override
    public Meal create(Meal meal) {
        return super.create(meal);
    }

    public void delete(int id) {
        // get() may throw NotFoundException or AccessDeniedException
        // if none of that happens, then delete the meal.
        get(id);
        super.delete(id);
    }

    public Meal get(int id) {
        Meal meal = super.get(id);
        try {
            assureUserIdConsistent(meal, authUserId());
        } catch (IllegalArgumentException e) {
            log.info("User {} has no access to the meal {}", authUserId(), meal);
            throw new AccessDeniedException("Current user has no access to the meal");
        }
        return meal;
    }

    public void update(Meal meal) {
        get(meal.getId());
        super.update(meal);
    }

    @Override
    public Collection<Meal> getAll() {
        return super.getAll(authUserId());
    }

}