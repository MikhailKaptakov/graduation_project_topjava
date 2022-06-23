package ru.graduation_project_topjava.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractBaseNamedEntity{

    @Column(name = "last_update", nullable = false)
    @NotNull
    private LocalDate lastUpdateDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    @OrderBy("name DESC")
    private List<Meal> meals;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("voteTime DESC")
    private List<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(String name, List<Meal> meals) {
        super(name);
        this.meals = meals;
        this.votes = Collections.emptyList();
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
}
