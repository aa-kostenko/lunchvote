package org.example.lunchvote.to;

import org.example.lunchvote.util.validation.NoHtml;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class LunchMenuItemTo extends BaseTo implements Serializable {

    @NotNull
    private Integer RestaurantId;

    @NotNull
    private LocalDate menuDate;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    @NoHtml
    protected String name;

    @NotNull
    @Range(min = 1, max = 50000)
    private BigDecimal price;

    public LunchMenuItemTo() {
    }

    @ConstructorProperties({"id", "restaurantId", "menuDate", "name", "price"})
    public LunchMenuItemTo(Integer id, Integer restaurantId, LocalDate menuDate, String name, BigDecimal price) {
        super(id);
        RestaurantId = restaurantId;
        this.menuDate = menuDate;
        this.name = name;
        this.price = price;
    }

    public Integer getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        RestaurantId = restaurantId;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunchMenuItemTo)) return false;
        LunchMenuItemTo that = (LunchMenuItemTo) o;
        return that.id == id && Objects.equals(RestaurantId, that.RestaurantId) && Objects.equals(menuDate, that.menuDate) && Objects.equals(name, that.name) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, RestaurantId, menuDate, name, price);
    }

    @Override
    public String toString() {
        return "LunchMenuItemTo{" +
                "id=" + id +
                ", RestaurantId=" + RestaurantId +
                ", menuDate=" + menuDate +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
