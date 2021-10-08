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

    System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
  }

  public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
    Map<LocalDate, Integer> mealsGroupDays = new HashMap<>();
    List<UserMeal> mealsFiltered = new ArrayList<>();
    List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();

    meals.forEach(day -> {
      mealsGroupDays.merge(day.getDateTime().toLocalDate(), day.getCalories(), (oldValue, newValue) -> oldValue + day.getCalories());
      if (TimeUtil.isBetweenHalfOpen(day.getDateTime().toLocalTime(), startTime, endTime)) {
        mealsFiltered.add(day);
      }
    });

    mealsFiltered.forEach(day -> userMealWithExcessList.add(new UserMealWithExcess(day.getDateTime(),
            day.getDescription(), day.getCalories(), mealsGroupDays.get(day.getDateTime().toLocalDate()) > caloriesPerDay)));

    return userMealWithExcessList;
  }

  public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
    Map<LocalDate, Integer> mealsGroupDays = meals.stream().collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
    return meals.stream()
            .filter(day -> TimeUtil.isBetweenHalfOpen(day.getDateTime().toLocalTime(), startTime, endTime))
            .map(day -> new UserMealWithExcess(day.getDateTime(), day.getDescription(), day.getCalories(), mealsGroupDays.get(day.getDateTime().toLocalDate()) > caloriesPerDay ))
            .collect(Collectors.toList());
  }
}
