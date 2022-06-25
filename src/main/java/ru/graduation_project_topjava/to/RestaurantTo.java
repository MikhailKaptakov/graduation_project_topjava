package ru.graduation_project_topjava.to;

import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.util.MealUtil;

import java.util.List;

public class RestaurantTo {

    private Long  id;

    private String name;

    private List<MealTo> mealTos;

    private int votes;

    public RestaurantTo(String name, List<MealTo> meals, int votes) {
        this.name = name;
        this.mealTos = meals;
        this.votes = votes;
    }

    public RestaurantTo(Long id, String name, List<MealTo> meals, int votes) {
        this.id = id;
        this.name = name;
        this.mealTos = meals;
        this.votes = votes;
    }

    public  RestaurantTo(Restaurant restaurant)  {
        this(restaurant.getId(), restaurant.getName(), MealUtil.createMealTosList(restaurant.getMeals()), restaurant.getVotesCount());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {return votes;}

    public List<MealTo> getMealTos() {
        return mealTos;
    }

}
