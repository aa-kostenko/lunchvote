package org.example.lunchvote;

import org.example.lunchvote.model.Restaurant;

import java.util.List;

public class RestaurantTestData {

    public static org.example.lunchvote.TestMatcher<Restaurant> RESTAURANT_MATCHER = org.example.lunchvote.TestMatcher.usingIgnoringFieldsComparator(Restaurant.class, "lunchMenuItems");

    public static final int RESTAURAUNT1_ID = 1;
    public static final int RESTAURAUNT2_ID = 2;

    public static final int NOT_FOUND = 10;

    public static final Restaurant restauraunt1 = new Restaurant(RESTAURAUNT1_ID, "Бумбараш");
    public static final Restaurant restauraunt2 = new Restaurant(RESTAURAUNT2_ID, "Диканька");

    public static final List<Restaurant> restauraunts = List.of(restauraunt1, restauraunt2);

    public static Restaurant getNew() {
        return new Restaurant(null, "Новый ресторан");
    }
}
