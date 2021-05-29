package org.example.lunchvote.to;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Objects;

public class VoteTo extends BaseTo implements  Serializable {

    @NotNull
    private Integer RestaurantId;

    public VoteTo() {
    }

    @ConstructorProperties({"id", "restaurantId"})
    public VoteTo(Integer id, Integer restaurantId) {
        super(id);
        RestaurantId = restaurantId;
    }

    public Integer getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        RestaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteTo)) return false;
        VoteTo voteTo = (VoteTo) o;
        return this.id == voteTo.id && Objects.equals(RestaurantId, voteTo.RestaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, RestaurantId);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", RestaurantId=" + RestaurantId +
                '}';
    }
}
