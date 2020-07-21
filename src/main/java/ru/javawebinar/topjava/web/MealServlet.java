package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.HardcodedMeals;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.dao.InMemoryMeals;
import ru.javawebinar.topjava.model.WithIdWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_CAP = 2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");

        List<WithIdWithExceed> list = InMemoryMeals.getInstance().getAllWithExceed(CALORIES_CAP);

        String action = request.getParameter("action");
        String id = request.getParameter("id");
        
        log.debug("action: " + action);
        log.debug("id: " + id);

        request.setAttribute("mealList", list);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}
