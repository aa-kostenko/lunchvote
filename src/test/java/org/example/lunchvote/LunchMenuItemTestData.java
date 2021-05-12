package org.example.lunchvote;

import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.model.Restaurant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.example.lunchvote.RestaurantTestData.restauraunt1;
import static org.example.lunchvote.RestaurantTestData.restauraunt2;

public class LunchMenuItemTestData {
    public static final TestMatcher<LunchMenuItem> LUNCH_MENU_ITEM_MATCHER =
            TestMatcher.usingEqualsComparator(LunchMenuItem.class);

    public static final int LUNCH_MENU1_ITEM1_ID = 1;
    public static final int LUNCH_MENU2_ITEM1_ID = LUNCH_MENU1_ITEM1_ID+6;
    public static final int NOT_FOUND = 10000;

    public static final LunchMenuItem lunchMenu1Item1 =
            new LunchMenuItem(
                    LUNCH_MENU1_ITEM1_ID,
                    restauraunt1,
                    "Борщ по-мегрельски",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("280.00"));

    public static final LunchMenuItem lunchMenu1Item2 =
            new LunchMenuItem(
                    LUNCH_MENU1_ITEM1_ID+1,
                    restauraunt1,
                    "Стейк из семги на углях",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("660.00"));

    public static final LunchMenuItem lunchMenu1Item3 =
            new LunchMenuItem(
                    LUNCH_MENU1_ITEM1_ID+2,
                    restauraunt1,
                    "Картошечка жареная с вешенками",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("160.00"));

    public static final LunchMenuItem lunchMenu2Item1 =
            new LunchMenuItem(
                    LUNCH_MENU2_ITEM1_ID,
                    restauraunt2,
                    "Борщ классический",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("375.00"));

    public static final LunchMenuItem lunchMenu2Item2 =
            new LunchMenuItem(
                    LUNCH_MENU2_ITEM1_ID+1,
                    restauraunt2,
                    "Котлета по-киевски",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("445.00"));

    public static final LunchMenuItem lunchMenu2Item3 =
            new LunchMenuItem(
                    LUNCH_MENU2_ITEM1_ID+2,
                    restauraunt2,
                    "Оливье",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("375.00"));

    public static final List<LunchMenuItem> lunchMenu1Items = List.of(lunchMenu1Item1, lunchMenu1Item2, lunchMenu1Item3);
    public static final List<LunchMenuItem> lunchMenu2Items = List.of(lunchMenu2Item1, lunchMenu2Item2, lunchMenu2Item3);

    public static LunchMenuItem getNew() {
        return new LunchMenuItem(
                null,
                restauraunt1,
                "Какое-то новое блюдо",
                LocalDate.of(2021, 1, 30),
                new BigDecimal("160.00")
        );
    }

}
