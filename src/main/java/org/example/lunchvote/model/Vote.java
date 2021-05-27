package org.example.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
/*
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "vote_date"},
        name = "votes_unique_user_date_idx")})

 */
public class Vote extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Transient
    private LocalDate voteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @PostLoad
    public void postLoad() {
        voteDate = LocalDate.from(dateTime);
    }

    public Vote(){
    }

    public Vote(Integer id, LocalDateTime dateTime, User user, Restaurant restaurant){
        super(id);
        this.dateTime = dateTime;
        this.voteDate = LocalDate.from(dateTime);
        this.user = user;
        this.restaurant = restaurant;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.voteDate = LocalDate.from(dateTime);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @JsonInclude
    public LocalDate getVoteDate() {
        return voteDate;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", voteDate=" + voteDate +
                ", restaurant=" + restaurant +
                '}';
    }
}
