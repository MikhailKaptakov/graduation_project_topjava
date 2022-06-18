package ru.graduation_project_topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "restaurant_id", "vote_date"},
        name = "votes_unique_vote_idx")})
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate  voteDate;

    @Column(name = "vote_time", nullable = false)
    @NotNull
    private LocalTime voteTime;

    /* todo при голосованиии  у пользователя сохраняем id ресторана за который голосуем
    * и сохраняем голос пользователя в базу через  юзер  айди и  айди  ресторана с датой ии  временем голосования
    * при голосовании пользователя  извлекаем  запись последнего голоса,   если  запись  сегодняшняя проверяем текущее
    * время,  если 11 часов нет,  удаляем голос  пользователя и создаём новый голос за выбранный  ресторан
    * Для ресторана:  для то объекта извлекаем  из базы данных все  голоса пользователей  за  сегодняшний день
    * и возвращаем размер списка
    *  */

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public LocalTime getVoteTime() {
        return voteTime;
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

    public void setVoteTime(LocalTime voteTime) {
        this.voteTime = voteTime;
    }
}
