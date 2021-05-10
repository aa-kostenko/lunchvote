package org.example.lunchvote.repository;

import org.example.lunchvote.model.LunchMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LunchMenuItemRepository extends JpaRepository<LunchMenuItem, Integer> {
    @Query("SELECT mi FROM LunchMenuItem mi WHERE mi.restaurant.id=:restaurantId "
            + " and mi.menuDate >= :startDate and mi.menuDate <= :endDate  ORDER BY mi.name ASC")
    List<LunchMenuItem> getAllBetween(@Param("restaurantId") int restaurantId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
