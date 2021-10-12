package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    void clear();

    void add(Meal meal);

    Meal get(int uuid);

    void delete(int uuid);

    void update(Meal meal);

    List<Meal> getList();

    int size();

}
