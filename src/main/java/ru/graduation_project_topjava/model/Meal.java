package ru.graduation_project_topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "restaurant_id", "meal_date"},
        name = "meals_unique_name_restaurant_daily_menu_idx")})
public class Meal extends AbstractBaseNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = Integer.MAX_VALUE)
    @NotNull
    private int price;

    @Column(name = "meal_date", nullable = false, columnDefinition = "date default current_date", updatable = false)
    @NotNull
    private LocalDate mealDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(long id, String name, int price, Restaurant restaurant) {
        super(name);
        this.id = id;
        this.price = price;
        this.mealDate = LocalDate.now();
        this.restaurant = restaurant;
    }

    public int getPrice() {
        return price;
    }

    public LocalDate getMealDate() {
        return mealDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMealDate(LocalDate mealDay) {
        this.mealDate = mealDay;
    }

    public void setRestaurant(Restaurant ownerRestaurant) {
        this.restaurant = ownerRestaurant;
    }

    public Long getRestaurantId() {
        return restaurant.getId();
    }
}
