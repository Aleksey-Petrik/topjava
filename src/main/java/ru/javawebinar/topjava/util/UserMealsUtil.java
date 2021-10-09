package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        List<UserMealWithExcess> mealsToSecond = filteredByCyclesSecond(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mealsGroupDays = new HashMap<>();
        List<UserMeal> mealsFiltered = new ArrayList<>();

        meals.forEach(day -> {
            mealsGroupDays.merge(day.getDate(), day.getCalories(), (oldValue, newValue) -> oldValue + day.getCalories());
            if (TimeUtil.isBetweenHalfOpen(day.getTime(), startTime, endTime)) {
                mealsFiltered.add(day);
            }
        });

        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        mealsFiltered.forEach(day -> userMealWithExcessList.add(new UserMealWithExcess(day.getDateTime(),
                day.getDescription(), day.getCalories(), mealsGroupDays.get(day.getDate()) > caloriesPerDay)));

        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByCyclesSecond(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mealsGroupDays = new HashMap<>();
        meals.forEach(dateTime -> mealsGroupDays.merge(dateTime.getDate(), dateTime.getCalories(), (oldValue, newValue) -> oldValue + dateTime.getCalories()));

        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        meals.forEach(dateTime -> {
            if (TimeUtil.isBetweenHalfOpen(dateTime.getTime(), startTime, endTime)) {
                userMealWithExcessList.add(new UserMealWithExcess(dateTime.getDateTime(),
                        dateTime.getDescription(), dateTime.getCalories(), mealsGroupDays.get(dateTime.getDate()) > caloriesPerDay));
            }
        });
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mealsGroupDays = meals.stream().collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(day -> TimeUtil.isBetweenHalfOpen(day.getTime(), startTime, endTime))
                .map(day -> new UserMealWithExcess(day.getDateTime(), day.getDescription(), day.getCalories(), mealsGroupDays.get(day.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
