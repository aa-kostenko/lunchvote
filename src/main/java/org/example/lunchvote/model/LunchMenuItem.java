package org.example.lunchvote.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lunch_menu_items",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "menu_date", "name"},
                name = "lunch_menu_items_unique_idx")})
public class LunchMenuItem extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @NotNull
    @Column(name = "menu_date")
    private LocalDate menuDate;

    @NotNull
    @Range(min = 1, max = 50000)
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public LunchMenuItem(){
    }

    public LunchMenuItem(Integer id, Restaurant restaurant, String name, LocalDate menuDate, BigDecimal price) {
        super(id, name);
        this.menuDate = menuDate;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "LunchMenuItem{" +
                "restaurant=" + restaurant +
                ", menuDate=" + menuDate +
                ", price=" + price +
                '}';
    }
}
