package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMeals;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithId;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealEditServlet extends HttpServlet {
    private static final Logger log = getLogger(MealEditServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // This fixed the encoding issue (request.set...)
        request.setCharacterEncoding("UTF-8");
        // Encoding here does not help with Cyrillic
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        MealWithId meal = null;

        try {
            Integer mealId = Integer.parseInt(Objects.requireNonNull(request.getParameter("mealid")));
            String description = Objects.requireNonNull(request.getParameter("description"));
            Integer calories = Integer.parseInt(Objects.requireNonNull(request.getParameter("calories")));
            LocalDateTime dateTime = TimeUtil.parse(Objects.requireNonNull(request.getParameter("datetime")));

            log.debug("mealid: " + mealId);
            log.debug("description: " + description);
            log.debug("calories: " + calories);
            log.debug("datetime: " + dateTime);

            if (mealId == 0) {
                meal = InMemoryMeals.getInstance().newMeal();
            } else {
                if ((meal = InMemoryMeals.getInstance().get(mealId)) == null) {
                    throw new IndexOutOfBoundsException("Meal does not exist");
                }
            }

            meal.setDescription(description);
            meal.setCalories(calories);
            meal.setDateTime(dateTime);

            InMemoryMeals.getInstance().save(meal);

        } catch (NullPointerException | NumberFormatException | DateTimeParseException | IndexOutOfBoundsException e) {
            log.debug("Malformed form data");

            if (meal == null) {
                meal = InMemoryMeals.getInstance().newMeal();
            }

            request.setAttribute("mealId", (meal.getId() == null ? 0 : meal.getId()));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealform.jsp").forward(request, response);

            return;
        }

        response.sendRedirect(request.getContextPath() + "/meals");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // This fixed the encoding issue (request.set...)
        request.setCharacterEncoding("UTF-8");
        // Encoding here does not help with Cyrillic
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        //log.debug("redirect to users");
        log.debug("forward to meal form");

        log.debug("Request URI: " + request.getRequestURI());

        String last = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);

        log.debug("last: " + last);

        if (last.equals("edit")) {
            Integer id = null;
            String idString = request.getParameter("id");
            // Tip: they say guava lib has a one-liner for this, though
            // I managed to put try/catch in one line here too :-)
            try { id = Integer.parseInt(idString); } catch (NumberFormatException e) {}

            if (id == null || InMemoryMeals.getInstance().notExists(id)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            MealWithId meal = InMemoryMeals.getInstance().get(id);

            request.setAttribute("mealId", meal.getId());
            request.setAttribute("meal", meal);

            log.debug("Meal (" + id + "): " + meal.getDescription() + " " + meal);
        }

        if (last.equals("add")) {
            Meal newMeal = new Meal(LocalDateTime.now(), "", 0);
            request.setAttribute("mealId", 0);
            request.setAttribute("meal", newMeal);
        }

        request.getRequestDispatcher("/mealform.jsp").forward(request, response);

    }
}
