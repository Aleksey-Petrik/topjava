package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private List<Meal> meals;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        meals = MealsUtil.getTestData();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");

        List<MealTo> mealsTo = MealsUtil.filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), MealsUtil.CALORIES_PER_DAY);

        if (action == null) {
            req.setAttribute("meals", mealsTo);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }

        switch (action) {
            case "delete":
                meals.removeIf(meal -> meal.getDateTime().toString().equals(uuid));
/*
                Meal mealDelete = meals.stream().filter(meal -> meal.getDateTime().toString().equals(uuid)).findFirst().get();
                if (mealDelete != null) {
                    meals.remove(mealDelete);
                }
*/
                resp.sendRedirect("meals");
                return;
        }

        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
