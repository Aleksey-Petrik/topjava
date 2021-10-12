package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ListStorage implements Storage {
    private static AtomicInteger id = new AtomicInteger(0);
    private ConcurrentHashMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    public ListStorage(List<Meal> storage) {
        for (Meal meal : storage) {
            this.storage.put(meal.getId(), meal);
        }
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void add(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public Meal get(int uuid) {
        return storage.get(uuid);
    }

    @Override
    public void delete(int uuid) {
        storage.remove(uuid);
    }

    @Override
    public void update(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    public static int generateID() {
        return id.incrementAndGet();
    }

}
