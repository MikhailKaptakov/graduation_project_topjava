package ru.graduation_project_topjava.to;

import ru.graduation_project_topjava.model.Meal;

public class MealTo {

    private Long id;

    private Long restaurantId;

    private String name;

    private int price;

    public MealTo(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public MealTo(Long id, String name, int price, long restaurantId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public MealTo(Meal meal) {
        this(meal.getId(), meal.getName(), meal.getPrice(), meal.getRestaurant().getId());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
