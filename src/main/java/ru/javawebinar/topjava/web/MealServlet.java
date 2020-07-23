package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.AccessDeniedException;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Cannot use Autowired because this class is not a Spring managed bean
        // it's being instantiated by servlet container and Spring knows nothing about it
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealRestController = appCtx.getBean(MealRestController.class);
        }
        log.debug("init(): mealRestController: " + (mealRestController == null ? "null" : "Ok"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String op = request.getParameter("op");

        if (op != null && op.equals("change-auth-user-id")) {
            String newUserId = request.getParameter("new-user-id");
            log.info("change-auth-user-id +++ {}", newUserId);
            if (newUserId != null) {
                try {
                    SecurityUtil.setAuthUserId(Integer.parseInt(newUserId));
                } catch (NumberFormatException e) {
                    // whatever
                }
            }
            response.sendRedirect("meals");
            return;
        }

        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
            SecurityUtil.authUserId(),
            LocalDateTime.parse(request.getParameter("dateTime")),
            request.getParameter("description"),
            Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        if (meal.isNew()) {
            mealRestController.create(meal);
        } else {
            try {
                mealRestController.update(meal);
            } catch (NotFoundException e) {
                log.info("Attempt to update non-existing meal {}", meal);
            } catch (AccessDeniedException e) {
                log.info("Attempt to update someone else's meal {}", meal);
            }
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("authUserId", SecurityUtil.authUserId());
        request.setAttribute("userIds", MealsUtil.userIds());

        String action = request.getParameter("action");

        int mealId = -1;

        try {
            switch (action == null ? "all" : action) {
                case "delete":
                    mealId = getId(request);
                    log.info("Delete {}", mealId);
                    mealRestController.delete(mealId);
                    response.sendRedirect("meals");
                    break;
                case "create":
                case "update":
                    final Meal meal = "create".equals(action) ?
                        new Meal(SecurityUtil.authUserId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                    break;
                case "all":
                default:
                    log.info("getAll (*)");
                    request.setAttribute("meals",
                        MealsUtil.getTos(mealRestController.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    break;
            }
        } catch (NotFoundException e) {
            log.info("Meal not found {}", mealId);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (AccessDeniedException e) {
            log.info("Meal access denied. authUserId {} {}", SecurityUtil.authUserId(), e.getMeal());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
