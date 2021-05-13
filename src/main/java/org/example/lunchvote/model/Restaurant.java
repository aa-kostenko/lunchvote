package org.example.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity{

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<LunchMenuItem> lunchMenuItems;

    public List<LunchMenuItem> getLunchMenuItems() {
        return lunchMenuItems;
    }

    public void setLunchMenuItems(List<LunchMenuItem> lunchMenuItems) {
        this.lunchMenuItems = lunchMenuItems;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
