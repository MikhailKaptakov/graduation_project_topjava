package ru.graduation_project_topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "restaurant_id", "meal_day"},
        name = "meals_unique_name_restaurant_daily_menu_idx")})
public class Meal extends AbstractBaseNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = Integer.MAX_VALUE)
    @NotNull
    private int price;

    @Column(name = "meal_day", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private LocalDate mealDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(String name, int price) {
        super(name);
        this.price = price;
        this.mealDay = LocalDate.now();
    }

    public Meal(long id, String name, int price, Restaurant restaurant) {
        super(name);
        this.id = id;
        this.price = price;
        this.mealDay = LocalDate.now();
        this.restaurant = restaurant;
    }

    public int getPrice() {
        return price;
    }

    public LocalDate getMealDay() {
        return mealDay;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMealDay(LocalDate mealDay) {
        this.mealDay = mealDay;
    }

    public void setRestaurant(Restaurant ownerRestaurant) {
        this.restaurant = ownerRestaurant;
    }
}
