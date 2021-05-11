package org.example.lunchvote.repository;

import org.example.lunchvote.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.example.lunchvote.RestaurantTestData.RESTAURANT_MATCHER;
import static org.example.lunchvote.RestaurantTestData.getNew;

public class RestaurantRepositoryTest extends AbstractRepositoryTest{
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
}
