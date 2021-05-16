package org.example.lunchvote.repository;

import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.example.lunchvote.LunchMenuItemTestData.*;
import static org.example.lunchvote.RestaurantTestData.RESTAURAUNT1_ID;
import static org.example.lunchvote.RestaurantTestData.RESTAURAUNT4_ID;
import static org.example.lunchvote.RestaurantTestData.restauraunt4;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LunchMenuItemTest  extends AbstractRepositoryTest{
    @Autowired
    LunchMenuItemRepository repository;

    @Test
    void create() {
        LunchMenuItem created = repository.save(getNew());
        int newId = created.id();
        LunchMenuItem newLunchMenuItem = getNew();
        newLunchMenuItem.setId(newId);
        LUNCH_MENU_ITEM_MATCHER.assertMatch(created, newLunchMenuItem);
        LUNCH_MENU_ITEM_MATCHER.assertMatch(repository.findById(newId).get(), newLunchMenuItem);
    }

    @Test
    void createDoubleRestaurantDateName() {
        assertThrows(DataAccessException.class, () ->
                repository.save(new LunchMenuItem(null, restauraunt4, "Рататуй", LocalDate.now(), new BigDecimal("366.00"))));
    }

    @Test
    void get() {
        LunchMenuItem lunchMenuItem = repository.findById(LUNCH_MENU1_DAY1_ITEM1_ID).get();
        LUNCH_MENU_ITEM_MATCHER.assertMatch(lunchMenuItem, lunchMenu1Day1Item1);
    }

    @Test
    void getAll() {
        LUNCH_MENU_ITEM_MATCHER.assertMatch(repository.findAll(), lunchMenuItems);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class,
                () -> repository
                        .findById(NOT_FOUND)
                        .orElseThrow(() -> new NotFoundException("not found Lunch Menu Item with id " + NOT_FOUND)));
    }

    @Test
    void geAllMenuBetweenOneDay(){
        List<LunchMenuItem> lunchMenuItemList = repository.getAllBetween(
                RESTAURAUNT1_ID,
                LocalDate.of(2021,1,30),
                LocalDate.of(2021,1,30));
        LUNCH_MENU_ITEM_MATCHER.assertMatch(lunchMenuItemList, lunchMenu1Day1Items);
    }

    @Test
    void geAllMenuBetweenTwoDays(){
        List<LunchMenuItem> lunchMenuItemList = repository.getAllBetween(
                RESTAURAUNT1_ID,
                LocalDate.of(2021,1,30),
                LocalDate.of(2021,1,31));
        LUNCH_MENU_ITEM_MATCHER.assertMatch(lunchMenuItemList, lunchMenu1Items);
    }

    @Test
    void geExclusiveHasMenuToday(){
        List<LunchMenuItem> lunchMenuItemList = repository.getAllBetween(
                RESTAURAUNT4_ID,
                LocalDate.now(),
                LocalDate.now());
        LUNCH_MENU_ITEM_MATCHER.assertMatch(lunchMenuItemList, lunchMenu4Items);
    }

    @Test
    void getAllMenuOnNoWorkPeriod(){
        List<LunchMenuItem> lunchMenuItemList = repository.getAllBetween(
                RESTAURAUNT1_ID,
                LocalDate.of(1900,1,30),
                LocalDate.of(1900,1,31));
        LUNCH_MENU_ITEM_MATCHER.assertMatch(lunchMenuItemList, List.of());
    }

}
