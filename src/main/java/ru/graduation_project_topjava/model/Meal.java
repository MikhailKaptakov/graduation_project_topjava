package ru.graduation_project_topjava.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference(value = "meal_restaurant_reference")
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(String name, int price) {
        super(name);
        this.price = price;
        this.mealDate = LocalDate.now();
    }

    public Meal (Meal meal) {
       this(meal.id, meal.name, meal.price, meal.mealDate, meal.restaurant);
    }

    public Meal(Long id, String name, int price, LocalDate mealDate, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.mealDate = mealDate;
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

    @JsonIgnore
    public Long getRestaurantId() {
        return restaurant.getId();
    }
}
