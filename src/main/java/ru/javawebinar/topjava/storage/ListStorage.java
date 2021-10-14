package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ListStorage implements Storage {
    private static final AtomicInteger id = new AtomicInteger(0);
    private ConcurrentMap<Integer, Meal> storage;

    public ListStorage(List<Meal> storage) {
        Objects.requireNonNull(storage);
        this.storage = storage.stream().collect(Collectors.toConcurrentMap(Meal::getId, meal -> meal));
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
