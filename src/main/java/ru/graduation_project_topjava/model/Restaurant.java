package ru.graduation_project_topjava.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"},
        name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractBaseNamedEntity{

    @Column(name = "last_update", nullable = false)
    @NotNull
    private LocalDate lastUpdateDate;

    @Transient
    @Valid
    private List<Meal> meals;

    @Transient
    @Valid
    private List<Vote> votes;

    public Restaurant() {
        this.lastUpdateDate = MIN;
        this.meals = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public Restaurant(String name) {
        super(name);
        this.lastUpdateDate = MIN;
        this.meals = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public Restaurant(Long id, String name, LocalDate lastUpdateDate, List<Meal> meals, List<Vote> votes) {
        super(id, name);
        this.lastUpdateDate = lastUpdateDate;
        this.meals = meals;
        this.votes = votes;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.id, restaurant.name, restaurant.lastUpdateDate, restaurant.meals, restaurant.votes);
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    @JsonGetter(value = "meals")
    public List<Meal> getMeals() {
        return meals;
    }

    @JsonGetter(value = "votes")
    public List<Vote> getVotes() {
        return votes;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @JsonSetter(value = "meals")
    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @JsonSetter(value = "votes")
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public int takeVotesCount() {
        return votes.size();
    }

    public void addMeal(Meal meal) {
        if (meals == null) {
            meals = new ArrayList<>();
        }
        meals.add(meal);
    }

    public void addVote(Vote vote) {
        if (votes == null) {
            votes = new ArrayList<>();
        }
        votes.add(vote);
    }


}
