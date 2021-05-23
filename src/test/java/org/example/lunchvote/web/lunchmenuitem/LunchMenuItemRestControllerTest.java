package org.example.lunchvote.web.lunchmenuitem;

import org.example.lunchvote.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.example.lunchvote.LunchMenuItemTestData.*;
import static org.example.lunchvote.RestaurantTestData.RESTAURAUNT4_ID;
import static org.example.lunchvote.TestUtil.userHttpBasic;
import static org.example.lunchvote.UserTestData.user1;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LunchMenuItemRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = LunchMenuItemRestController.REST_URL + '/';

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + LUNCH_MENU1_DAY1_ITEM1_ID)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(LUNCH_MENU_ITEM_MATCHER.contentJson(lunchMenu1Day1Item1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + LUNCH_MENU1_DAY1_ITEM1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAllTodayForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL+"allToday")
                .param("restaurantId", String.valueOf(RESTAURAUNT4_ID))
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(LUNCH_MENU_ITEM_MATCHER.contentJson(lunchMenu4Items));
    }
}
