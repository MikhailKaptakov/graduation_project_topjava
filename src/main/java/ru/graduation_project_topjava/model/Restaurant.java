package ru.graduation_project_topjava.model;

import javax.persistence.*;
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
    private List<Meal> meals;

    @Transient
    private List<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(String name, List<Meal> meals) {
        super(name);
        this.meals = meals;
        this.lastUpdateDate = MIN;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public int getVotesCount() {
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
