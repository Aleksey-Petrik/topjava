package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.ListStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new ListStorage(MealsUtil.getTestData());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = req.getParameter("action");

        int uuid;
        try {
            uuid = Integer.parseInt(req.getParameter("uuid"));
        } catch (NumberFormatException e) {
            uuid = 0;
        }

        List<MealTo> mealsTo = MealsUtil.getTos(storage.getList(), MealsUtil.CALORIES_PER_DAY);

        if (action == null) {
            req.setAttribute("meals", mealsTo);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }

        Meal meal = null;
        switch (action) {
            case "delete":
                log.info("Delete id = {}", uuid);
                storage.delete(uuid);
                resp.sendRedirect("meals");
                return;
            case "edit":
                meal = storage.get(uuid);
                break;
            case "add":
                meal = new Meal(ListStorage.generateID(), LocalDateTime.now(), "", 0);
                break;
        }
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        int uuid = Integer.parseInt(req.getParameter("uuid"));

        if (uuid != 0) {
            Meal oldMeal;
            try {
                oldMeal = storage.get(uuid);
            } catch (NoSuchElementException e) {
                oldMeal = null;
            }

            String dateTime = req.getParameter("dateTime");
            String descriptions = req.getParameter("descriptions");
            String calories = req.getParameter("calories");

            Meal newMeal = new Meal(uuid, TimeUtil.parse(dateTime), descriptions, Integer.parseInt(calories));

            if (oldMeal == null) {
                storage.add(newMeal);
                log.info("Add {}", newMeal);
            } else if (!newMeal.equals(oldMeal)) {
                storage.update(newMeal);
                log.info("Update {}", newMeal);
            }
        }
        resp.sendRedirect("meals");
    }
}
