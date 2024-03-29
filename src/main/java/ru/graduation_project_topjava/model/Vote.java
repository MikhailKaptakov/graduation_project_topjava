package ru.graduation_project_topjava.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "vote_date"},
        name = "votes_unique_vote_idx")})
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @JsonBackReference (value = "vote_user_reference")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @JsonBackReference(value = "vote_restaurant_reference")
    private Restaurant restaurant;

    @Column(name = "vote_date", nullable = false, columnDefinition = "date default current_date")
    @NotNull
    private LocalDate  voteDate;

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
        this.voteDate = LocalDate.now();
    }

    public Vote(Long id, User user, Restaurant restaurant, LocalDate voteDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.voteDate = voteDate;
    }

    public Vote(Vote vote) {
        this(vote.id, vote.user, vote.restaurant, vote.voteDate);
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }

    @JsonIgnore
    public Long getRestaurantId() {
        return restaurant.getId();
    }

    @JsonIgnore
    public Long getUserId() {
        return user.getId();
    }



}
