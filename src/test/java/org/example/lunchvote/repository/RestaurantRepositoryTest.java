package org.example.lunchvote.repository;

import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.model.Restaurant;
import org.example.lunchvote.util.exception.NotFoundException;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;

import static org.example.lunchvote.LunchMenuItemTestData.LUNCH_MENU_ITEM_MATCHER;
import static org.example.lunchvote.LunchMenuItemTestData.lunchMenu1Items;
import static org.example.lunchvote.LunchMenuItemTestData.lunchMenu2Items;
import static org.example.lunchvote.LunchMenuItemTestData.lunchMenu4Items;
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

    @Test
    void getAllWithLunchMenuItems(){
        List<Restaurant> restaurants = repository.geAlltWithLunchMenuItems();
        RESTAURANT_MATCHER.assertMatch(restaurants, List.of(restauraunt1, restauraunt2, restauraunt4));
        RESTAURANT_MATCHER.assertMatch(restaurants.get(0), restauraunt1);
        RESTAURANT_MATCHER.assertMatch(restaurants.get(1), restauraunt2);
        RESTAURANT_MATCHER.assertMatch(restaurants.get(2), restauraunt4);
        LUNCH_MENU_ITEM_MATCHER.assertMatch(restaurants.get(0).getLunchMenuItems(), lunchMenu1Items);
        LUNCH_MENU_ITEM_MATCHER.assertMatch(restaurants.get(1).getLunchMenuItems(), lunchMenu2Items);
        LUNCH_MENU_ITEM_MATCHER.assertMatch(restaurants.get(2).getLunchMenuItems(), lunchMenu4Items);
    }

    @Test
    void geAllHasMenuAnyDate(){
        List<Restaurant> restaurants = repository.geAllHasMenuAnyDate();
        RESTAURANT_MATCHER.assertMatch(restaurants, List.of(restauraunt1, restauraunt2, restauraunt4));
        assertThrows(LazyInitializationException.class, () -> restaurants.get(0).getLunchMenuItems().get(0));
        assertThrows(LazyInitializationException.class, () -> restaurants.get(1).getLunchMenuItems().get(0));
        assertThrows(LazyInitializationException.class, () -> restaurants.get(2).getLunchMenuItems().get(0));
    }

    @Test
    void geAllHasMenuBetweenOneDay(){
        List<Restaurant> restaurants = repository.geAllHasMenuBetween(
                LocalDate.of(2021,1,30),
                LocalDate.of(2021,1,30));
        RESTAURANT_MATCHER.assertMatch(restaurants, List.of(restauraunt1, restauraunt2));
        assertThrows(LazyInitializationException.class, () -> restaurants.get(0).getLunchMenuItems().get(0));
    }

    @Test
    void geAllHasMenuBetweenTwoDay(){
        List<Restaurant> restaurants = repository.geAllHasMenuBetween(
                LocalDate.of(2021,1,30),
                LocalDate.of(2021,1,31));
        RESTAURANT_MATCHER.assertMatch(restaurants, List.of(restauraunt1, restauraunt2));
        assertThrows(LazyInitializationException.class, () -> restaurants.get(0).getLunchMenuItems().get(0));
    }

    @Test
    void geExclusiveHasMenuToday(){
        List<Restaurant> restaurants = repository.geAllHasMenuBetween(
                LocalDate.now(),
                LocalDate.now());
        RESTAURANT_MATCHER.assertMatch(restaurants, List.of(restauraunt4));
        assertThrows(LazyInitializationException.class, () -> restaurants.get(0).getLunchMenuItems().get(0));
    }

    @Test
    void geAllHasMenuOnNoWorkPeriod(){
        List<Restaurant> restaurants = repository.geAllHasMenuBetween(
                LocalDate.of(1900,1,30),
                LocalDate.of(1900,1,31));
        RESTAURANT_MATCHER.assertMatch(restaurants, List.of());
    }

}
