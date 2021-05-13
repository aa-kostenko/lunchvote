package org.example.lunchvote.repository;

import org.example.lunchvote.model.Restaurant;
import org.example.lunchvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static org.example.lunchvote.LunchMenuItemTestData.LUNCH_MENU_ITEM_MATCHER;
import static org.example.lunchvote.LunchMenuItemTestData.lunchMenu1Items;
import static org.example.lunchvote.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private RestaurantRepository repository;

    @Test
    void create() {
        Restaurant created = repository.save(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.findById(newId).get(), newRestaurant);
    }

    @Test
    void duplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                repository.save(new Restaurant(null, "Бумбараш")));
    }

    @Test
    void get() {
        Restaurant restaurant = repository.findById(RESTAURAUNT1_ID).get();
        RESTAURANT_MATCHER.assertMatch(restaurant, restauraunt1);
    }

    @Test
    void getAll() {
        RESTAURANT_MATCHER.assertMatch(repository.findAll(), restauraunts);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class,
                () -> repository
                        .findById(NOT_FOUND)
                        .orElseThrow(() -> new NotFoundException("not found Restaurant with id " + RESTAURAUNT1_ID)));
    }

    @Test
    void getWithLunchMenuItems(){
        Restaurant restaurant = repository.getWithLunchMenuItems(RESTAURAUNT1_ID).get();
        RESTAURANT_MATCHER.assertMatch(restaurant, restauraunt1);
        LUNCH_MENU_ITEM_MATCHER.assertMatch(restaurant.getLunchMenuItems(), lunchMenu1Items);
    }

}
