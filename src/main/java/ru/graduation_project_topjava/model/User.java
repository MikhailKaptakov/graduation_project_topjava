package ru.graduation_project_topjava.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "users",  uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "unique_email")})
public class User extends AbstractBaseNamedEntity {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 128)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "last_vote_date", nullable = false)
    @NotNull
    private LocalDate lastVoteDate;

    @Column(name = "last_vote_time", nullable = false)
    @NotNull
    private LocalTime lastVoteTime;

    @Column(name = "voted_restaurant_id")
    private LocalDateTime votedRestaurantId;

    public User() {
    }

    public User(String name, String email, String password, Set<Role> roles) {
        super(name);
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.lastVoteDate = MIN;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public LocalDate getLastVoteDate() {
        return lastVoteDate;
    }

    public LocalTime getLastVoteTime() {
        return lastVoteTime;
    }

    public LocalDateTime getVotedRestaurantId() {
        return votedRestaurantId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setLastVoteDate(LocalDate lastVoteDate) {
        this.lastVoteDate = lastVoteDate;
    }

    public void setLastVoteTime(LocalTime lastVoteTime) {
        this.lastVoteTime = lastVoteTime;
    }

    public void setVotedRestaurantId(LocalDateTime votedRestaurantId) {
        this.votedRestaurantId = votedRestaurantId;
    }
}
