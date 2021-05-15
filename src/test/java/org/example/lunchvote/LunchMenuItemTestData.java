package org.example.lunchvote;

import org.example.lunchvote.model.LunchMenuItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.lunchvote.RestaurantTestData.*;

public class LunchMenuItemTestData {
    public static final TestMatcher<LunchMenuItem> LUNCH_MENU_ITEM_MATCHER =
            TestMatcher.usingIgnoringFieldsComparator(LunchMenuItem.class, "restaurant");

    public static final int LUNCH_MENU1_DAY1_ITEM1_ID = 1;
    public static final int LUNCH_MENU1_DAY2_ITEM1_ID = LUNCH_MENU1_DAY1_ITEM1_ID + 3;
    public static final int LUNCH_MENU2_DAY1_ITEM1_ID = LUNCH_MENU1_DAY2_ITEM1_ID + 3;
    public static final int LUNCH_MENU2_DAY2_ITEM1_ID = LUNCH_MENU2_DAY1_ITEM1_ID + 3;
    public static final int LUNCH_MENU4_DAY1_ITEM1_ID = LUNCH_MENU2_DAY2_ITEM1_ID + 3;
    public static final int NOT_FOUND = 10000;

    public static final LunchMenuItem lunchMenu1Day1Item1 =
            new LunchMenuItem(
                    LUNCH_MENU1_DAY1_ITEM1_ID,
                    restauraunt1,
                    "Борщ по-мегрельски",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("280.00"));

    public static final LunchMenuItem lunchMenu1Day1Item2 =
            new LunchMenuItem(
                    LUNCH_MENU1_DAY1_ITEM1_ID + 1,
                    restauraunt1,
                    "Стейк из семги на углях",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("660.00"));

    public static final LunchMenuItem lunchMenu1Day1Item3 =
            new LunchMenuItem(
                    LUNCH_MENU1_DAY1_ITEM1_ID + 2,
                    restauraunt1,
                    "Картошечка жареная с вешенками",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("160.00"));

    public static final LunchMenuItem lunchMenu1Day2Item1 =
            new LunchMenuItem(
                    LUNCH_MENU1_DAY2_ITEM1_ID,
                    restauraunt1,
                    "Грузинский рыбный суп",
                    LocalDate.of(2021, 1, 31),
                    new BigDecimal("380.00"));

    public static final LunchMenuItem lunchMenu1Day2Item2 =
            new LunchMenuItem(
                    LUNCH_MENU1_DAY2_ITEM1_ID + 1,
                    restauraunt1,
                    "Шашлык из курицы",
                    LocalDate.of(2021, 1, 31),
                    new BigDecimal("260.00"));

    public static final LunchMenuItem lunchMenu1Day2Item3 =
            new LunchMenuItem(
                    LUNCH_MENU1_DAY2_ITEM1_ID + 2,
                    restauraunt1,
                    "Картофельное пюре",
                    LocalDate.of(2021, 1, 31),
                    new BigDecimal("160.00"));

    public static final LunchMenuItem lunchMenu2Day1Item1 =
            new LunchMenuItem(
                    LUNCH_MENU2_DAY1_ITEM1_ID,
                    restauraunt2,
                    "Борщ классический",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("385.00"));

    public static final LunchMenuItem lunchMenu2Day1Item2 =
            new LunchMenuItem(
                    LUNCH_MENU2_DAY1_ITEM1_ID + 1,
                    restauraunt2,
                    "Котлета по-киевски",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("445.00"));

    public static final LunchMenuItem lunchMenu2Day1Item3 =
            new LunchMenuItem(
                    LUNCH_MENU2_DAY1_ITEM1_ID + 2,
                    restauraunt2,
                    "Оливье",
                    LocalDate.of(2021, 1, 30),
                    new BigDecimal("375.00"));

    public static final LunchMenuItem lunchMenu2Day2Item1 =
            new LunchMenuItem(
                    LUNCH_MENU2_DAY2_ITEM1_ID,
                    restauraunt2,
                    "Лапша домашняя",
                    LocalDate.of(2021, 1, 31),
                    new BigDecimal("295.00"));

    public static final LunchMenuItem lunchMenu2Day2Item2 =
            new LunchMenuItem(
                    LUNCH_MENU2_DAY2_ITEM1_ID + 1,
                    restauraunt2,
                    "Пельмени",
                    LocalDate.of(2021, 1, 31),
                    new BigDecimal("325.00"));

    public static final LunchMenuItem lunchMenu2Day2Item3 =
            new LunchMenuItem(
                    LUNCH_MENU2_DAY2_ITEM1_ID + 2,
                    restauraunt2,
                    "Селедка под шубой",
                    LocalDate.of(2021, 1, 31),
                    new BigDecimal("365.00"));

    public static final LunchMenuItem lunchMenu4Day1Item1 =
            new LunchMenuItem(
                    LUNCH_MENU4_DAY1_ITEM1_ID,
                    restauraunt4,
                    "Лапша китайская",
                    LocalDate.now(),
                    new BigDecimal("133.00"));

    public static final LunchMenuItem lunchMenu4Day1Item2 =
            new LunchMenuItem(
                    LUNCH_MENU4_DAY1_ITEM1_ID + 1,
                    restauraunt4,
                    "Вареники",
                    LocalDate.now(),
                    new BigDecimal("244.00"));

    public static final LunchMenuItem lunchMenu4Day1Item3 =
            new LunchMenuItem(
                    LUNCH_MENU4_DAY1_ITEM1_ID + 2,
                    restauraunt4,
                    "Рататуй",
                    LocalDate.now(),
                    new BigDecimal("366.00"));

    public static final List<LunchMenuItem> lunchMenu1Day1Items = List.of(lunchMenu1Day1Item1, lunchMenu1Day1Item2, lunchMenu1Day1Item3);
    public static final List<LunchMenuItem> lunchMenu1Day2Items = List.of(lunchMenu1Day2Item1, lunchMenu1Day2Item2, lunchMenu1Day2Item3);
    public static final List<LunchMenuItem> lunchMenu1Items = Stream.concat(lunchMenu1Day1Items.stream(), lunchMenu1Day2Items.stream()).collect(Collectors.toList());

    public static final List<LunchMenuItem> lunchMenu2Day1Items = List.of(lunchMenu2Day1Item1, lunchMenu2Day1Item2, lunchMenu2Day1Item3);
    public static final List<LunchMenuItem> lunchMenu2Day2Items = List.of(lunchMenu2Day2Item1, lunchMenu2Day2Item2, lunchMenu2Day2Item3);
    public static final List<LunchMenuItem> lunchMenu2Items = Stream.concat(lunchMenu2Day1Items.stream(), lunchMenu2Day2Items.stream()).collect(Collectors.toList());

    public static final List<LunchMenuItem> lunchMenu4Day1Items = List.of(lunchMenu4Day1Item1, lunchMenu4Day1Item2, lunchMenu4Day1Item3);
    public static final List<LunchMenuItem> lunchMenu4Items = lunchMenu4Day1Items;

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
