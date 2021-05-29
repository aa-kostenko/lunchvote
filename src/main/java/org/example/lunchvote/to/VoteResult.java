package org.example.lunchvote.to;

import org.example.lunchvote.model.Restaurant;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

public class VoteResult {
    private Restaurant restaurant;
    private LocalDate date;
    private Long voteCount;

    @ConstructorProperties({"restaurant", "date", "voteCount"})
    public VoteResult(Restaurant restaurant, LocalDate date, Long voteCount) {
        this.restaurant = restaurant;
        this.date = date;
        this.voteCount = voteCount;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "VoteResult{" +
                "restaurant=" + restaurant +
                ", date=" + date +
                ", voteCount=" + voteCount +
                '}';
    }
}
