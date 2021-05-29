package org.example.lunchvote.util;

import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.to.LunchMenuItemTo;

public class LunchMenuItemUtil {
    public static LunchMenuItemTo asTo(LunchMenuItem lunchMenuItem) {
        return new LunchMenuItemTo(
                lunchMenuItem.getId(),
                lunchMenuItem.getRestaurant().getId(),
                lunchMenuItem.getMenuDate(),
                lunchMenuItem.getName(),
                lunchMenuItem.getPrice()
        );
    }
}
