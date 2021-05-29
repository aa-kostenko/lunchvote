package org.example.lunchvote.web.lunchmenuitem;

import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.repository.LunchMenuItemRepository;
import org.example.lunchvote.to.LunchMenuItemTo;
import org.example.lunchvote.util.LunchMenuItemUtil;
import org.example.lunchvote.util.exception.NotFoundException;
import org.example.lunchvote.web.AbstractControllerTest;
import org.example.lunchvote.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.example.lunchvote.LunchMenuItemTestData.*;
import static org.example.lunchvote.RestaurantTestData.NOT_FOUND;
import static org.example.lunchvote.RestaurantTestData.RESTAURAUNT4_ID;
import static org.example.lunchvote.RestaurantTestData.restauraunt1;
import static org.example.lunchvote.TestUtil.readFromJson;
import static org.example.lunchvote.TestUtil.userHttpBasic;
import static org.example.lunchvote.UserTestData.admin1;
import static org.example.lunchvote.UserTestData.user1;
import static org.example.lunchvote.util.exception.ErrorType.ACCESS_DENIED;
import static org.example.lunchvote.util.exception.ErrorType.VALIDATION_ERROR;
import static org.example.lunchvote.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT_DAY_MENU_NAME;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LunchMenuItemRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = LunchMenuItemRestController.REST_URL + '/';

    @Autowired
    private LunchMenuItemRepository lunchMenuItemRepository;

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

    @Test
    void createWithLocation() throws Exception {
        LunchMenuItem newLunchMenuItem = getNew();

        LunchMenuItemTo newLunchMenuItemTo = LunchMenuItemUtil.asTo(newLunchMenuItem);

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newLunchMenuItemTo))
                .with(userHttpBasic(admin1)));

        LunchMenuItem created = readFromJson(action, LunchMenuItem.class);
        int newId = created.id();
        newLunchMenuItem.setId(newId);
        LUNCH_MENU_ITEM_MATCHER.assertMatch(created, newLunchMenuItem);
    }

    @Test
    void createInvalid() throws Exception {
        LunchMenuItem invalid = new LunchMenuItem(null, restauraunt1, null, null, null);
        LunchMenuItemTo invalidTo = LunchMenuItemUtil.asTo(invalid);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        LunchMenuItem invalid = new LunchMenuItem(
                null,
                restauraunt1,
                "Борщ по-мегрельски",
                LocalDate.of(2021, 1, 30),
                new BigDecimal("280.00"));

        LunchMenuItemTo invalidTo = LunchMenuItemUtil.asTo(invalid);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_RESTAURANT_DAY_MENU_NAME)));
    }

    @Test
    void createUnsafe() throws Exception {
        LunchMenuItem invalid = new LunchMenuItem(
                null,
                restauraunt1,
                "<script>alert(123)</script>",
                LocalDate.of(2021, 1, 30),
                new BigDecimal("280.00"));

        LunchMenuItemTo invalidTo = LunchMenuItemUtil.asTo(invalid);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void createNoAdmin() throws Exception {
        LunchMenuItem newLunchMenuItem = getNew();
        LunchMenuItemTo newLunchMenuItemTo = LunchMenuItemUtil.asTo(newLunchMenuItem);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newLunchMenuItemTo))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(errorType(ACCESS_DENIED));
    }

    @Test
    void update() throws Exception {
        LunchMenuItem updated = getUpdated();
        LunchMenuItemTo updatedTo = LunchMenuItemUtil.asTo(updated);

        perform(MockMvcRequestBuilders.put(REST_URL + LUNCH_MENU1_DAY1_ITEM1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo))
                .with(userHttpBasic(admin1)))
                .andExpect(status().isNoContent());

        LUNCH_MENU_ITEM_MATCHER.assertMatch(lunchMenuItemRepository.findById(LUNCH_MENU1_DAY1_ITEM1_ID).get(), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        LunchMenuItem invalid = new LunchMenuItem(null, restauraunt1, null, null, null);
        LunchMenuItemTo invalidTo = LunchMenuItemUtil.asTo(invalid);
        perform(MockMvcRequestBuilders.put(REST_URL + LUNCH_MENU1_DAY1_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        LunchMenuItem invalid = new LunchMenuItem(
                LUNCH_MENU1_DAY1_ITEM1_ID,
                restauraunt1,
                "Стейк из семги на углях",
                LocalDate.of(2021, 1, 30),
                new BigDecimal("280.00"));

        LunchMenuItemTo invalidTo = LunchMenuItemUtil.asTo(invalid);

        perform(MockMvcRequestBuilders.put(REST_URL + LUNCH_MENU1_DAY1_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo))
                .with(userHttpBasic(admin1)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        LunchMenuItem invalid = new LunchMenuItem(
                LUNCH_MENU1_DAY1_ITEM1_ID,
                restauraunt1,
                "<script>alert(123)</script>",
                LocalDate.of(2021, 1, 30),
                new BigDecimal("280.00"));

        LunchMenuItemTo invalidTo = LunchMenuItemUtil.asTo(invalid);

        perform(MockMvcRequestBuilders.put(REST_URL + LUNCH_MENU1_DAY1_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateNoAdmin() throws Exception {
        LunchMenuItem updated = getUpdated();
        LunchMenuItemTo updatedTo = LunchMenuItemUtil.asTo(updated);

        perform(MockMvcRequestBuilders.put(REST_URL + LUNCH_MENU1_DAY1_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(errorType(ACCESS_DENIED));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + LUNCH_MENU1_DAY1_ITEM1_ID)
                .with(userHttpBasic(admin1)))
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class,
                () -> lunchMenuItemRepository
                        .findById(LUNCH_MENU1_DAY1_ITEM1_ID)
                        .orElseThrow(() -> new NotFoundException("not found Lunch menu item with id " + LUNCH_MENU1_DAY1_ITEM1_ID)));
    }

    @Test
    void deleteNoAdmin() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + LUNCH_MENU1_DAY1_ITEM1_ID)
                .with(userHttpBasic(user1)))
                .andExpect(status().isUnauthorized())
                .andExpect(errorType(ACCESS_DENIED));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(admin1)))
                .andExpect(status().isUnprocessableEntity());
    }

}
